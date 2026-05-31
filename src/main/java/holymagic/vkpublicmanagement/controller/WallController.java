package holymagic.vkpublicmanagement.controller;

import holymagic.vkpublicmanagement.model.SuccessResponse;
import holymagic.vkpublicmanagement.model.wall.Post;
import holymagic.vkpublicmanagement.service.WallService;
import holymagic.vkpublicmanagement.util.LinkManager;
import holymagic.vkpublicmanagement.validator.WallParamValidator;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/wall")
public class WallController {

    private final WallService wallService;
    private final WallParamValidator wallParamValidator;
    private final LinkManager linkManager;

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

    @GetMapping("/get/unpopular")
    public ResponseEntity<Map<Long, String>> getUnpopularPosts(
            @RequestParam(required = false, defaultValue = "20") int count,
            @RequestParam(required = false, defaultValue = "0") int offset
    ) {
        wallParamValidator.validateCountUnpopular(count);
        wallParamValidator.validateOffset(offset);
        return ResponseEntity.ok(wallService.getPostsWithNoLikes(count, offset));
    }

    @GetMapping("/get/hashtags")
    public ResponseEntity<List<String>> getHashtagsFromWall(
            @RequestParam @NotBlank(message = "hashtag can't be empty") String hashtag
    ) {
        wallParamValidator.validateHashtag(hashtag);
        return ResponseEntity.ok(wallService.getHashtagsFromWall(hashtag));
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

    @GetMapping("/search/all")
    public ResponseEntity<List<Post>> searchAllPostsOnWall(
            @RequestParam @NotBlank(message = "query can't be empty") String query
    ) {
        return ResponseEntity.ok(wallService.searchAllPostsOnWall(query));
    }

    @GetMapping("/photo/ids")
    public ResponseEntity<Set<Long>> getPhotoIds(
            @RequestParam(required = false, defaultValue = "20") int count,
            @RequestParam(required = false, defaultValue = "0") int offset
    ) {
        wallParamValidator.validateCount(count);
        wallParamValidator.validateOffset(offset);
        return ResponseEntity.ok(wallService.getPhotoIds(count, offset));
    }

    @GetMapping("/photo/ids/links")
    public ResponseEntity<List<String>> getPhotoLinks(
            @RequestParam(required = false, defaultValue = "20") int count,
            @RequestParam(required = false, defaultValue = "0") int offset
    ) {
        wallParamValidator.validateCount(count);
        wallParamValidator.validateOffset(offset);
        Set<Long> photoIds = wallService.getPhotoIds(count, offset);
        List<String> photoLinks = linkManager.createPhotoLinks(photoIds);
        return ResponseEntity.ok(photoLinks);
    }

    @PostMapping("/photo/ids/links")
    public ResponseEntity<SuccessResponse> savePhotoLinks(
            @RequestParam(required = false, defaultValue = "20") int count,
            @RequestParam(required = false, defaultValue = "0") int offset
    ) {
        wallParamValidator.validateCount(count);
        wallParamValidator.validateOffset(offset);
        Set<Long> photoIds = wallService.getPhotoIds(count, offset);
        List<String> photoLinks = linkManager.createPhotoLinks(photoIds);
        linkManager.saveLinksToFile(photoLinks);
        return ResponseEntity.ok(new SuccessResponse("photo links were saved successfully"));
    }

    @PostMapping("/photo/ids/all")
    public ResponseEntity<SuccessResponse> saveAllPhotoIds() {
        Set<Long> allPhotoIds = wallService.getAllPhotoIds();
        linkManager.saveLinksToFile(allPhotoIds);
        return ResponseEntity.ok(new SuccessResponse("all photo ids were saved successfully"));
    }

}
