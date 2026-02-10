package holymagic.vkpublicmanagement.model.user.subscription;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GroupSubscription extends Subscription {
    private Long id;
    private static String type = "page";
    private String name;
}
