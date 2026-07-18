package holymagic.vkpublicmanagement.model.wall.attachment;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class Photo {
    private Long id;
    private Long date;
    @JsonProperty("album_id")
    private Long albumId;
    @JsonProperty("post_id")
    private Long postId;
    private String text;
    @JsonProperty("orig_photo")
    private OriginalPhoto originalPhoto;
}
