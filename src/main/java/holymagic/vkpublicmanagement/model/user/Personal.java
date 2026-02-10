package holymagic.vkpublicmanagement.model.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Personal {
    private Integer political;
    @JsonProperty("langs")
    private List<String> languages;
    private String religion;
    @JsonProperty("inspired_by")
    private String inspiredBy;
    @JsonProperty("people_main")
    private Integer traits;
    @JsonProperty("life_main")
    private Integer goals;
}
