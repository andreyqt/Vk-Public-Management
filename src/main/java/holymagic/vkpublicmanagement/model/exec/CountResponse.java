package holymagic.vkpublicmanagement.model.exec;

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
    private int requests;
    private int offset;
    private String message;
}
