package holymagic.vkpublicmanagement.model.wall.attachment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PhotoAttachment extends Attachment {

    private static String type = "photo";
    private Photo photo;

}
