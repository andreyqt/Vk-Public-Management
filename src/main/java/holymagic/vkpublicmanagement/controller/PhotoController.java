package holymagic.vkpublicmanagement.controller;

import holymagic.vkpublicmanagement.model.SuccessResponse;
import holymagic.vkpublicmanagement.model.wall.attachment.Photo;
import holymagic.vkpublicmanagement.service.PhotoService;
import holymagic.vkpublicmanagement.util.LinkManager;
import holymagic.vkpublicmanagement.validator.WallParamValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/photo")
public class PhotoController {

    private final PhotoService photoService;
    private final WallParamValidator wallParamValidator;
    private final LinkManager linkManager;

    @Value("${saved_photo_path_2}")
    private String photoIdsPath;

    @GetMapping("/albums/wall")
    public ResponseEntity<List<Photo>> getWallAlbum(
            @RequestParam(required = false, defaultValue = "20") int count,
            @RequestParam(required = false, defaultValue = "0") int offset,
            @RequestParam(required = false, defaultValue = "false") boolean rev,
            @RequestParam(required = false, defaultValue = "-225773763") String ownerId
    ) {
        wallParamValidator.validateCount(count);
        wallParamValidator.validateOffset(offset);
        return ResponseEntity.ok(photoService.getWallAlbumPhotos(count, offset, rev, ownerId));
    }

    @PostMapping("/albums/wall/ids/all")
    public ResponseEntity<SuccessResponse> getWallAllAlbumPhotoIds(
            @RequestParam(required = false, defaultValue = "-225773763") String ownerId
    ) throws InterruptedException {
        Set<Long> photoIds = photoService.getAllWallPhotoIds(ownerId);
        linkManager.saveLinksToFile(photoIds, photoIdsPath);
        return ResponseEntity.ok(new SuccessResponse("all photo ids were saved"));
    }

}
