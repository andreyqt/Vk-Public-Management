package holymagic.vkpublicmanagement.service;

import holymagic.vkpublicmanagement.model.user.AuthUri;
import holymagic.vkpublicmanagement.model.user.User;
import holymagic.vkpublicmanagement.model.user.UserToken;
import holymagic.vkpublicmanagement.model.user.subscription.Subscription;
import jakarta.websocket.SessionException;
import jakarta.ws.rs.core.UriBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static holymagic.vkpublicmanagement.model.ParameterizedTypeReferences.FOLLOWER_RESPONSE_REF;
import static holymagic.vkpublicmanagement.model.ParameterizedTypeReferences.SUBSCRIPTION_RESPONSE_REF;
import static holymagic.vkpublicmanagement.model.ParameterizedTypeReferences.USER_RESPONSE_REF;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final ExchangeService exchangeService;

    @Value("${auth_uri}")
    private String authUri;
    @Value("${auth_redirect_uri}")
    private String authRedirectUri;
    @Value("${app_id}")
    private String appId;
    @Value("${api_version}")
    private String version;
    @Value("${user_token_msg}")
    private String defaultTokenMsg;
    @Value("${user_token_ttl}")
    private Long userTokenTtl;

    public AuthUri init(String scope) {
        URI uri = UriBuilder.fromUri(authUri)
                .queryParam("client_id", appId)
                .queryParam("redirect_uri", authRedirectUri)
                .queryParam("response_type", "token")
                .queryParam("scope", scope)
                .queryParam("display", "page")
                .queryParam("v", version)
                .build();
        return new AuthUri(uri);
    }

    public UserToken extractToken(String fragment, String scope) {
        Pattern tokenPattern = Pattern.compile("access_token=([^&]+)");
        Matcher tokenMatcher = tokenPattern.matcher(fragment);
        String token = tokenMatcher.find() ? tokenMatcher.group(1) : null;

        Pattern userIdPattern = Pattern.compile("user_id=([^&]+)");
        Matcher userIdMatcher = userIdPattern.matcher(fragment);
        Long userId = Long.valueOf(userIdMatcher.find() ? userIdMatcher.group(1) : "0");

        return new UserToken(userId, defaultTokenMsg, token, scope, userTokenTtl);
    }

    public User getUser(Long userId, String token) {
        URI uri = exchangeService.provideGetUserUri(userId, token);
        return exchangeService.getData(uri, USER_RESPONSE_REF).getFirst();
    }

    public List<Long> getFollowers(Long userId, String token) {
        URI uri = exchangeService.provideGetFollowersUri(userId, token);
        return exchangeService.getData(uri, FOLLOWER_RESPONSE_REF).getItems();
    }

    public List<Subscription> getSubscriptions(Long userId, String token) {
        URI uri = exchangeService.provideGetSubscriptionsUri(userId, token);
        List<Subscription> response = exchangeService.getData(uri, SUBSCRIPTION_RESPONSE_REF).getItems();
        log.info("received {} subscriptions", response.size());
        return response;
    }

}
