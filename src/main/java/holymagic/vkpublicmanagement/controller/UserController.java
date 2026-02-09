package holymagic.vkpublicmanagement.controller;

import holymagic.vkpublicmanagement.model.user.AuthUri;
import holymagic.vkpublicmanagement.model.user.UserToken;
import holymagic.vkpublicmanagement.service.UserService;
import holymagic.vkpublicmanagement.validator.AuthUriValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final AuthUriValidator authUriValidator;

    @GetMapping("/init")
    public ResponseEntity<AuthUri> init() {
        return ResponseEntity.ok(userService.init());
    }

    @PostMapping("/exchange")
    public ResponseEntity<UserToken> exchange(@RequestBody String uri) {
        log.info("uri: {}", uri);
        authUriValidator.validateAuthUri(uri);
        String fragment = uri.split("#")[1];
        return ResponseEntity.ok(userService.extractToken(fragment));
    }

}
