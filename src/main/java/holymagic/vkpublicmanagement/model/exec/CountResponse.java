package holymagic.vkpublicmanagement.model.exec;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CountResponse {
    private int count;
    @JsonProperty("first_timestamp")
    private Long firstTimestamp;
    @JsonProperty("last_timestamp")
    private Long lastTimestamp;
}
