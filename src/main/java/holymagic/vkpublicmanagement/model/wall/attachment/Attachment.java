package holymagic.vkpublicmanagement.model.wall.attachment;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = PhotoAttachment.class, name = "photo"),
        @JsonSubTypes.Type(value = AudioAttachment.class, name = "audio")
})
public abstract class Attachment {
}
