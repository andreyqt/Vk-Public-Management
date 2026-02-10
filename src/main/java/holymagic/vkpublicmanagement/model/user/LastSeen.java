package holymagic.vkpublicmanagement.model.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LastSeen {
    private Long time;
    private Integer platform;
}
