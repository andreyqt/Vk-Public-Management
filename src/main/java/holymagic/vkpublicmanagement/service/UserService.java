package holymagic.vkpublicmanagement.service;

import holymagic.vkpublicmanagement.model.user.AuthUri;
import holymagic.vkpublicmanagement.model.user.UserToken;
import jakarta.ws.rs.core.UriBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

}
