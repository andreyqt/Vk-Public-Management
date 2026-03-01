package holymagic.vkpublicmanagement.service;

import holymagic.vkpublicmanagement.exception.EmptyResponseException;
import holymagic.vkpublicmanagement.mapper.CountResponseMapper;
import holymagic.vkpublicmanagement.model.exec.CountResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;

import static holymagic.vkpublicmanagement.model.ParameterizedTypeReferences.EXEC_COUNT_REF;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExecuteService {

    private final ExchangeService exchangeService;
    private final CountResponseMapper countResponseMapper;

    @Value("${exec_get_count_path}")
    private String getCountPath;
    @Value("${exec_get_count_over_period_path}")
    private String getCountOverPeriodPath;
    @Value("${exec_get_full_count_path}")
    private String getFullCountPath;


    public CountResponse getCount(String query, String domain, int offset) {
        URI uri = exchangeService.provideGetCountExecUri(getCountPath, query, domain, offset);
        CountResponse response = exchangeService.getDataForExec(uri, EXEC_COUNT_REF);
        validateCountResponse(response);
        return response;
    }

    public CountResponse getCountOverPeriod(long beginUnixTimestamp, long endUnixTimestamp,
                                            String query, String domain, int offset) {
        URI uri = exchangeService.provideGetCountOverPeriodExecUri(getCountOverPeriodPath,
                beginUnixTimestamp, endUnixTimestamp,
                query, domain, offset);
        CountResponse response = exchangeService.getDataForExec(uri, EXEC_COUNT_REF);
        validateCountResponse(response);
        return response;
    }

    public CountResponse getFullCount(long beginUnixTimestamp, long endUnixTimestamp,
                                      String domain, int offset) {
        URI uri = exchangeService.provideGetFullCountUri(getFullCountPath,
                beginUnixTimestamp, endUnixTimestamp, domain, offset);
        CountResponse response = exchangeService.getDataForExec(uri, EXEC_COUNT_REF);
        while (!response.getMessage().equals("success")) {
            offset = response.getOffset();
            URI newUri = exchangeService.provideGetFullCountUri(getFullCountPath,
                    beginUnixTimestamp, endUnixTimestamp, domain, offset);
            CountResponse newResponse = exchangeService.getDataForExec(newUri, EXEC_COUNT_REF);
            countResponseMapper.update(response, newResponse);
        }
        return response;
    }

    private void validateCountResponse(CountResponse response) {
        if (response.getCount() == 0) {
            throw new EmptyResponseException("No results found");
        }
    }

}
