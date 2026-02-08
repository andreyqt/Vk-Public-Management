package holymagic.vkpublicmanagement.model.wall;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WallResponse {
    private Integer count;
    private List<Post> items;
}
