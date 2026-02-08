package holymagic.vkpublicmanagement.controller;

import holymagic.vkpublicmanagement.model.wall.Post;
import holymagic.vkpublicmanagement.service.WallService;
import holymagic.vkpublicmanagement.validator.WallParamValidator;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/wall")
public class WallController {

    private final WallService wallService;
    private final WallParamValidator wallParamValidator;

    @GetMapping("/get")
    public ResponseEntity<List<Post>> getPostsFromWall(
            @RequestParam(required = false, defaultValue = "20") int count,
            @RequestParam(required = false, defaultValue = "0") int offset
    ) {
        wallParamValidator.validateCount(count);
        wallParamValidator.validateOffset(offset);
        return ResponseEntity.ok(wallService.getPostsFromWall(count, offset));
    }

    @GetMapping("/get/id/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable String id) {
        return ResponseEntity.ok(wallService.getPostById(id));
    }

    @GetMapping("/search")
    public ResponseEntity<List<Post>> searchPostsOnWall(
            @RequestParam @NotBlank(message = "query can't be empty") String query,
            @RequestParam(required = false, defaultValue = "20") int count,
            @RequestParam(required = false, defaultValue = "0") int offset
    ) {
        wallParamValidator.validateCount(count);
        wallParamValidator.validateOffset(offset);
        return ResponseEntity.ok(wallService.searchPostsOnWall(query, count, offset));
    }

}
