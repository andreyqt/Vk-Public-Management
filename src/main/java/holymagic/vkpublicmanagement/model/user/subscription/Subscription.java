package holymagic.vkpublicmanagement.model.user.subscription;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = UserSubscription.class, name = "profile"),
        @JsonSubTypes.Type(value = GroupSubscription.class, name = "page")
})
public abstract class Subscription {
}
