package holymagic.vkpublicmanagement.controller;

import holymagic.vkpublicmanagement.exception.SessionExpiredException;
import holymagic.vkpublicmanagement.model.user.AuthUri;
import holymagic.vkpublicmanagement.model.user.User;
import holymagic.vkpublicmanagement.model.user.UserToken;
import holymagic.vkpublicmanagement.model.user.subscription.Subscription;
import holymagic.vkpublicmanagement.service.UserService;
import holymagic.vkpublicmanagement.validator.AuthUriValidator;
import jakarta.servlet.http.HttpSession;
import jakarta.websocket.SessionException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final AuthUriValidator authUriValidator;

    @GetMapping("/init")
    public ResponseEntity<AuthUri> init(
            @RequestParam(required = false, defaultValue = "wall") String scope,
            HttpSession session) {
        authUriValidator.validateScope(scope);
        session.setAttribute("scope", scope);
        return ResponseEntity.ok(userService.init(scope));
    }

    @PostMapping("/exchange")
    public ResponseEntity<UserToken> exchange(@RequestBody String uri, HttpSession session) {
        log.info("received uri: {}", uri);
        authUriValidator.validateAuthUri(uri);
        String fragment = uri.split("#")[1];
        String scope = authUriValidator.validateSessionScope(session);
        return ResponseEntity.ok(userService.extractToken(fragment, scope));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<User> getUser(
            @PathVariable Long id,
            @RequestParam String token) {
        return ResponseEntity.ok(userService.getUser(id, token));
    }

    @GetMapping("/id/{id}/followers")
    public ResponseEntity<List<Long>> getFollowers(
            @PathVariable Long id,
            @RequestParam String token) {
        return ResponseEntity.ok(userService.getFollowers(id, token));
    }

    @GetMapping("/id/{id}/subscriptions")
    public ResponseEntity<List<Subscription>> getSubscriptions(
            @PathVariable Long id,
            @RequestParam String token) {
        return ResponseEntity.ok(userService.getSubscriptions(id, token));
    }

}
