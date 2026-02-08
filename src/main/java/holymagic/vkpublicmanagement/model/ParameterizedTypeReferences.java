package holymagic.vkpublicmanagement.model;

import holymagic.vkpublicmanagement.model.wall.PostResponse;
import holymagic.vkpublicmanagement.model.wall.WallResponse;
import org.springframework.core.ParameterizedTypeReference;

public class ParameterizedTypeReferences {

    public static final ParameterizedTypeReference<Root<WallResponse>> WALL_RESPONSE_REF =
            new ParameterizedTypeReference<>() {};

    public static final ParameterizedTypeReference<Root<PostResponse>> POST_RESPONSE_REF =
            new ParameterizedTypeReference<>() {};

}
