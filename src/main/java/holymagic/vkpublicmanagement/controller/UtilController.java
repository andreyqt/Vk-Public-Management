package holymagic.vkpublicmanagement.controller;

import holymagic.vkpublicmanagement.dto.HashtagDto;
import holymagic.vkpublicmanagement.dto.TwoLinkDto;
import holymagic.vkpublicmanagement.util.LinkManager;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/util")
public class UtilController {

    private final LinkManager linkManager;

    @PostMapping("/photos_not_on_wall")
    public ResponseEntity<List<String>> getPhotoLinksNotOnWall(
            @RequestBody TwoLinkDto links
            ) {
        String wallIdsPath = links.getFirstLink();
        String albumIdsPath = links.getSecondLink();
        log.info("got 2 links: \n {} \n {} \n", wallIdsPath, albumIdsPath);
        Set<Long> wallIds = linkManager.readIdsFromFile(wallIdsPath);
        Set<Long> albumIds = linkManager.readIdsFromFile(albumIdsPath);
        albumIds.removeAll(wallIds);
        List<String> result = linkManager.createPhotoLinks(albumIds);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/wall_links")
    public ResponseEntity<List<String>> getWallLinks(@Valid @RequestBody HashtagDto hashtags) {
        return ResponseEntity.ok(linkManager.createWallSearchLinks(hashtags.getHashtags()));
    }

}
