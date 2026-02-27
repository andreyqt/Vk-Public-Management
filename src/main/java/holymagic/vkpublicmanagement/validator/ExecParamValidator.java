package holymagic.vkpublicmanagement.validator;

import holymagic.vkpublicmanagement.exception.ParamValidationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class ExecParamValidator {

    @Value("${exec_init_timestamp}")
    private long initialTimestamp;

    public void validateGetCountTimestamps(long beginTimestamp, long endTimestamp) {
        if (beginTimestamp > endTimestamp || beginTimestamp < initialTimestamp ||
            endTimestamp > Instant.now().getEpochSecond()) {
            throw new ParamValidationException("Invalid timestamp(s)");
        }
    }

}
