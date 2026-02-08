package holymagic.vkpublicmanagement.model.wall.attachment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OriginalPhoto {
    private int height;
    private int width;
    private String url;
    private String type;
}
