package holymagic.vkpublicmanagement.service;

import holymagic.vkpublicmanagement.dto.CountResponseDto;
import holymagic.vkpublicmanagement.exception.EmptyResponseException;
import holymagic.vkpublicmanagement.mapper.CountResponseMapper;
import holymagic.vkpublicmanagement.model.exec.CountResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.time.LocalDateTime;

import static holymagic.vkpublicmanagement.model.ParameterizedTypeReferences.EXEC_COUNT_REF;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExecuteService {

    private final ExchangeService exchangeService;
    private final CountResponseMapper countResponseMapper;

    @Value("${exec_get_count_path}")
    private String getCountPath;

    public CountResponse getCount(String query, String domain, int offset) {
        URI uri = exchangeService.provideGetCountExecUri(getCountPath, query, domain, offset);
        CountResponse response = exchangeService.getDataForExec(uri, EXEC_COUNT_REF);
        validateCountResponse(response);
        return response;
    }

    public CountResponse getTotalCount(String query, String domain) {
        CountResponse firstResponse = getCount(query, domain, 0);
        if (firstResponse.getCount() < 100) {
            log.info("received total count in one request");
            return firstResponse;
        }
        int requests = 1, offset = 100, count = 100;
        boolean needMore = true;
        Long firstTimestamp = firstResponse.getFirstTimestamp();
        Long lastTimestamp = firstResponse.getLastTimestamp();
        while (needMore) {
            CountResponse newResponse = getCount(query, domain, offset);
            lastTimestamp = newResponse.getLastTimestamp();
            requests++;
            offset+=100;
            count+= newResponse.getCount();
            if (newResponse.getCount() < 100) {
                needMore = false;
            }
        }
        log.info("received total count in {} requests", requests);
        return new CountResponse(count, firstTimestamp, lastTimestamp);
    }

    private void validateCountResponse(CountResponse response) {
        if (response.getCount() == 0) {
            throw new EmptyResponseException("No results found");
        }
    }

}
