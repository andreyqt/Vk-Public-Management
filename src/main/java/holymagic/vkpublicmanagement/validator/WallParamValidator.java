package holymagic.vkpublicmanagement.validator;

import holymagic.vkpublicmanagement.exception.ParamValidationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class WallParamValidator {

    @Value("${get_wall_max_count}")
    private int maxCount;

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

}
