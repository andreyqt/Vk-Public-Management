package holymagic.vkpublicmanagement.model;

import holymagic.vkpublicmanagement.model.user.FollowerResponse;
import holymagic.vkpublicmanagement.model.user.SubscriptionResponse;
import holymagic.vkpublicmanagement.model.user.User;
import holymagic.vkpublicmanagement.model.wall.PostResponse;
import holymagic.vkpublicmanagement.model.wall.WallResponse;
import org.springframework.core.ParameterizedTypeReference;

import java.util.List;

public class ParameterizedTypeReferences {

    public static final ParameterizedTypeReference<Root<WallResponse>> WALL_RESPONSE_REF =
            new ParameterizedTypeReference<>() {};

    public static final ParameterizedTypeReference<Root<PostResponse>> POST_RESPONSE_REF =
            new ParameterizedTypeReference<>() {};

    public static final ParameterizedTypeReference<Root<List<User>>> USER_RESPONSE_REF =
            new ParameterizedTypeReference<>() {};

    public static final ParameterizedTypeReference<Root<FollowerResponse>> FOLLOWER_RESPONSE_REF =
            new ParameterizedTypeReference<>() {};

    public static final ParameterizedTypeReference<Root<SubscriptionResponse>> SUBSCRIPTION_RESPONSE_REF =
            new ParameterizedTypeReference<>() {};

}
