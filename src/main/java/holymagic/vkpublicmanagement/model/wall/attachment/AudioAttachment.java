package holymagic.vkpublicmanagement.model.wall.attachment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AudioAttachment extends Attachment {

    private static String type = "audio";
    private Audio audio;

}
