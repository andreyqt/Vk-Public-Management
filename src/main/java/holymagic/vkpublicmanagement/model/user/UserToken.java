package holymagic.vkpublicmanagement.model.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserToken {

    private Long userId;
    private String message;
    private String token;
    private String scope;
    private Long timeToLive;
    private LocalDateTime createdAt;

    public UserToken(Long UserId, String message, String token, String scope, Long timeToLive) {
        this.userId = UserId;
        this.message = message;
        this.token = token;
        this.scope = scope;
        this.timeToLive = timeToLive;
        this.createdAt = LocalDateTime.now();
    }

}
