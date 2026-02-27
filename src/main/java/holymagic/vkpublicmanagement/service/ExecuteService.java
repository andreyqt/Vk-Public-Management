package holymagic.vkpublicmanagement.service;

import holymagic.vkpublicmanagement.exception.EmptyResponseException;
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

    @Value("${exec_get_count_path}")
    private String getCountPath;

    public CountResponse getCount(String query, String domain) {
        URI uri = exchangeService.provideGetCountExecUri(getCountPath, query, domain);
        CountResponse response = exchangeService.getDataForExec(uri, EXEC_COUNT_REF);
        validateCountResponse(response);
        return response;
    }

    private void validateCountResponse(CountResponse response) {
        if (response.getCount() == 0) {
            throw new EmptyResponseException("No results found");
        }
        if (response.getMessage().isEmpty()) {
            response.setMessage("success");
        }
    }

}
