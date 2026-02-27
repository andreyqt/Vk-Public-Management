package holymagic.vkpublicmanagement.service;

import holymagic.vkpublicmanagement.model.Root;
import jakarta.ws.rs.core.UriBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import static holymagic.vkpublicmanagement.model.user.UserFields.FIELDS;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExchangeService {

    private final RestClient restClient;

    @Value("${api_version}")
    private String version;
    @Value("${service_key}")
    private String accessToken;
    @Value("${my_public_domain}")
    private String myDomain;
    @Value("${my_public_owner_id}")
    private String ownerId;

    @Value("${get_from_wall_path}")
    private String getFromWallPath;
    @Value("${get_by_id_path}")
    private String getByIdPath;
    @Value("${wall_search_path}")
    private String wallSearchPath;

    @Value("${get_user_path}")
    private String getUserPath;
    @Value("${get_followers_path}")
    private String getFollowersPath;
    @Value("${get_subscriptions_path}")
    private String getSubscriptionsPath;

    public <T> T getData(URI uri, ParameterizedTypeReference<Root<T>> reference) {
        return restClient.get()
                .uri(uri)
                .retrieve()
                .body(reference)
                .getResponse();
    }

    public <T> T getDataForExec(URI uri, ParameterizedTypeReference<Root<T>> reference) {
        return restClient.post()
                .uri(uri)
                .body("{}")
                .retrieve()
                .body(reference)
                .getResponse();
    }

    public URI provideGetFromWallUri(int count, int offset) {
        return UriBuilder.fromPath(getFromWallPath)
                .queryParam("count", count).queryParam("offset", offset)
                .queryParam("access_token", accessToken)
                .queryParam("v", version).queryParam("domain", myDomain)
                .build();
    }

    public URI provideGetByIdUri(String id) {
        return UriBuilder.fromPath(getByIdPath)
                .queryParam("posts", ownerId + "_" + id)
                .queryParam("access_token", accessToken)
                .queryParam("v", version)
                .build();
    }

    public URI provideWallSearchUri(String query, int count, int offset) {
        return UriBuilder.fromPath(wallSearchPath)
                .queryParam("v", version).queryParam("access_token", accessToken)
                .queryParam("owner_id", ownerId).queryParam("domain", myDomain)
                .queryParam("query", URLEncoder.encode(query, StandardCharsets.UTF_8))
                .queryParam("count", count).queryParam("offset", offset).build();
    }

    public URI provideGetUserUri(Long userId, String token) {
        return UriBuilder.fromPath(getUserPath)
                .queryParam("access_token", token)
                .queryParam("user_ids", userId)
                .queryParam("fields", FIELDS)
                .queryParam("v", version)
                .build();
    }

    public URI provideGetFollowersUri(Long userId, String token) {
        return UriBuilder.fromPath(getFollowersPath)
                .queryParam("user_id", userId)
                .queryParam("access_token", token)
                .queryParam("v", version)
                .build();
    }

    public URI provideGetSubscriptionsUri(Long userId, String token) {
        return UriBuilder.fromPath(getSubscriptionsPath)
                .queryParam("user_id", userId)
                .queryParam("access_token", token)
                .queryParam("v", version)
                .queryParam("extended", "1")
                .build();
    }

    public URI provideGetCountExecUri(String path, String query, String domain) {
        return UriBuilder.fromPath(path)
                .queryParam("query", URLEncoder.encode(query, StandardCharsets.UTF_8))
                .queryParam("domain", domain)
                .queryParam("access_token", accessToken).queryParam("v", version)
                .build();
    }

}
