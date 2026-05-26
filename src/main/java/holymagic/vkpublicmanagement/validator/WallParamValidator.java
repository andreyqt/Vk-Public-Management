package holymagic.vkpublicmanagement.validator;

import holymagic.vkpublicmanagement.exception.ParamValidationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class WallParamValidator {

    @Value("${get_wall_max_count}")
    private int maxCount;
    @Value("${get_wall_max_count_unpopular}")
    public int maxCountUnpopular;

    public void validateOffset(int offset) {
        if (offset < 0) {
            throw new ParamValidationException("offset can't be negative");
        }
    }

    public void validateCount(int count) {
        if (count < 0 || count > maxCount) {
            throw new ParamValidationException("count must be between 0 and " + maxCount);
        }
    }

    public void validateCountUnpopular(int count) {
        if (count < 0 || count > maxCountUnpopular) {
            throw new ParamValidationException("count for unpopular posts must be between 0 and " + maxCountUnpopular);
        }
    }

    public void validateHashtag(String hashtag) {
        if (!hashtag.startsWith("#")) {
            throw new ParamValidationException("hashtag must start with '#'");
        }
        if (hashtag.contains(" ")) {
            throw new ParamValidationException("hashtag must not contain spaces");
        }
    }

}
