package holymagic.vkpublicmanagement.model.photo;

import holymagic.vkpublicmanagement.model.wall.attachment.Photo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PhotoResponse {
    private Integer count;
    private List<Photo> items;
}
