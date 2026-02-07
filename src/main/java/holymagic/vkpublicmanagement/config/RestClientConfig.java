package holymagic.vkpublicmanagement.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClient;

import java.net.http.HttpClient;
import java.time.Duration;
import java.time.LocalDateTime;

@Slf4j
@Configuration
public class RestClientConfig {

    @Value("${base_url}")
    private String baseUrl;
    @Value("${rest_client_timeout}")
    private long timeout;

    @Bean
    public HttpClient httpClient() {
        return HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(timeout))
                .build();
    }

    @Bean
    public JdkClientHttpRequestFactory jdkClientHttpRequestFactory(HttpClient httpClient) {
        return new JdkClientHttpRequestFactory(httpClient);
    }

    @Bean
    public RestClient restClient(JdkClientHttpRequestFactory jdkClientHttpRequestFactory) {
        return RestClient.builder()
                .baseUrl(baseUrl)
                .defaultHeaders(header -> {
                    header.add("Accept", "application/json");
                })
                .defaultStatusHandler(HttpStatusCode::is4xxClientError, handleClientError())
                .defaultStatusHandler(HttpStatusCode::is5xxServerError, handleServerError())
                .defaultStatusHandler(HttpStatusCode::is2xxSuccessful, handleSuccess())
                .requestFactory(jdkClientHttpRequestFactory)
                .build();
    }

    public RestClient.ResponseSpec.ErrorHandler handleClientError() {
        return (request, response) -> {
            HttpStatusCode statusCode = response.getStatusCode();
            String statusText = response.getStatusText();
            log.error("Bad request! \n method: {} \n uri: {} \n code: {}, {} \n timestamp: {}",
                    request.getMethod(), request.getURI(), statusCode, statusText, LocalDateTime.now());
            throw new HttpClientErrorException(statusCode, statusText);
        };
    }

    public RestClient.ResponseSpec.ErrorHandler handleServerError() {
        return (_, response) -> {
            HttpStatusCode statusCode = response.getStatusCode();
            String statusText = response.getStatusText();
            log.error("Server failed to complete request! \n code: {}, text: {} \n timestamp: {}",
                    statusCode, statusText, LocalDateTime.now()
            );
            throw new HttpServerErrorException(statusCode, statusText);
        };
    }

    public RestClient.ResponseSpec.ErrorHandler handleSuccess() {
        return (request, _) -> log.info("Made successful request! \n method: {} \n uri: {} \n timestamp: {}",
                request.getMethod(), request.getURI(), LocalDateTime.now());
    }

}
