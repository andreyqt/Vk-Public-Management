package holymagic.vkpublicmanagement.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TwoLinkDto {
    @NotBlank(message = "link can't be blank")
    private String firstLink;
    @NotBlank(message = "link can't be blank")
    private String secondLink;
}
