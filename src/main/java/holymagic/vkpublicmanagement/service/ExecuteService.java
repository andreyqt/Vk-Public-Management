package holymagic.vkpublicmanagement.service;

import holymagic.vkpublicmanagement.dto.CountResponseDto;
import holymagic.vkpublicmanagement.exception.EmptyResponseException;
import holymagic.vkpublicmanagement.mapper.CountResponseMapper;
import holymagic.vkpublicmanagement.model.exec.CountResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;

import static holymagic.vkpublicmanagement.model.ParameterizedTypeReferences.EXEC_COUNT_REF;

@Service
@RequiredArgsConstructor
public class ExecuteService {

    private final ExchangeService exchangeService;
    private final CountResponseMapper countResponseMapper;

    @Value("${exec_get_count_path}")
    private String getCountPath;

    public CountResponseDto getCount(String query, String domain, int offset) {
        URI uri = exchangeService.provideGetCountExecUri(getCountPath, query, domain, offset);
        CountResponse response = exchangeService.getDataForExec(uri, EXEC_COUNT_REF);
        validateCountResponse(response);
        return countResponseMapper.toDto(response);
    }

    private void validateCountResponse(CountResponse response) {
        if (response.getCount() == 0) {
            throw new EmptyResponseException("No results found");
        }
    }

}
