package holymagic.vkpublicmanagement.model.wall;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import holymagic.vkpublicmanagement.model.wall.attachment.Attachment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    private long date;
    private long id;
    @JsonProperty("is_pinned")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer isPinned;
    @JsonProperty("is_deleted")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean isDeleted;
    private Comment comments;
    private String type;
    private List<Attachment> attachments;
    @JsonProperty("owner_id")
    private Integer ownerId;
    @JsonProperty("post_type")
    private PostType postType;
    private Repost reposts;
    private String text;
    private View views;
    private Like likes;
}
