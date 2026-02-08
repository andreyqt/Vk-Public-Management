package holymagic.vkpublicmanagement.model.wall.attachment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Audio {
    private Long id;
    private String title;
    private String artist;
    private Integer duration;
}
