package holymagic.vkpublicmanagement.service;

import holymagic.vkpublicmanagement.model.Root;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ExchangeServiceTest {

    @InjectMocks
    private ExchangeService exchangeService;

    @Mock
    private RestClient restClient;
    @Mock
    private RestClient.RequestHeadersSpec requestHeadersSpec;
    @Mock
    private RestClient.RequestHeadersUriSpec requestHeadersUriSpec;
    @Mock
    private RestClient.ResponseSpec responseSpec;

    private URI testUri;
    private ArgumentCaptor<URI> uriCaptor;
    private ParameterizedTypeReference<Root<String>> testReference;

    @BeforeEach
    public void setUp() {
        uriCaptor = ArgumentCaptor.forClass(URI.class);
        when(restClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(uriCaptor.capture())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
    }

    @Test
    public void getDataTest() {
        URI testUri = URI.create("http://localhost:8080/");
        String expectedData = "data received";
        Root<String> testRoot = new Root<>(expectedData);
        when(responseSpec.body(testReference)).thenReturn(testRoot);
        String actualData = exchangeService.getData(testUri, testReference);

        verify(restClient, times(1)).get();
        verify(requestHeadersUriSpec, times(1)).uri(uriCaptor.capture());
        verify(requestHeadersSpec, times(1)).retrieve();
        verify(responseSpec, times(1)).body(testReference);
        URI capturedUri = uriCaptor.getValue();
        assertEquals(testUri, capturedUri);
        assertEquals(expectedData, actualData);
    }

}
