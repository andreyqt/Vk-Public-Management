package holymagic.vkpublicmanagement.validator;

import holymagic.vkpublicmanagement.exception.AuthUriSyntaxException;
import holymagic.vkpublicmanagement.exception.SessionExpiredException;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AuthUriValidator {

    @Value("${validator_start_pattern}")
    private String startPattern;
    @Value("${validator_token_pattern}")
    private String tokenPattern;
    @Value("${validator_ttl_pattern}")
    private String ttlPattern;
    @Value("${validator_user_id_pattern}")
    private String userIdPattern;

    public void validateAuthUri(String authUri) {
        if (authUri == null || authUri.isEmpty()) {
            throw new AuthUriSyntaxException("AuthUri is null or empty");
        }
        if (!authUri.startsWith(startPattern)) {
            throw new AuthUriSyntaxException("invalid authUri beginning pattern");
        }
        if (!authUri.contains(tokenPattern)) {
            throw new AuthUriSyntaxException("missing authUri token pattern");
        }
        if (!authUri.contains(ttlPattern)) {
            throw new AuthUriSyntaxException("missing authUri ttl pattern");
        }
        if (!authUri.contains(userIdPattern)) {
            throw new AuthUriSyntaxException("missing authUri userId pattern");
        }
    }

    public void validateScope(String scope) {
        if (!allowedScopes.contains(scope)) {
            throw new AuthUriSyntaxException("invalid scope: " + scope);
        }
    }

    public String validateSessionScope(HttpSession session) {
        String scope = (String) session.getAttribute("scope");
        if (scope == null) {
            throw new SessionExpiredException("Session has expired, you have to start again!");
        }
        return scope;
    }

    private final List<String> allowedScopes = List.of(
            "notify", "friends", "photos", "audio", "video", "stories", "pages", "menu",
            "status", "notes", "messages", "wall", "ads", "offline", "docs", "groups", "notifications",
            "stats", "email", "market", "phone"
    );

}
