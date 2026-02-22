package holymagic.vkpublicmanagement.model.user;

import holymagic.vkpublicmanagement.model.user.subscription.Subscription;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SubscriptionResponse {
    private Integer count;
    private List<Subscription> items;
}
