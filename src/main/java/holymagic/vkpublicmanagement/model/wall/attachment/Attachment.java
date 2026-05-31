package holymagic.vkpublicmanagement.model.wall.attachment;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = PhotoAttachment.class, name = "photo"),
        @JsonSubTypes.Type(value = AudioAttachment.class, name = "audio"),
        @JsonSubTypes.Type(value = DocAttachment.class, name = "doc"),
        @JsonSubTypes.Type(value = VideoAttachment.class, name = "video")
})
public abstract class Attachment {
}
