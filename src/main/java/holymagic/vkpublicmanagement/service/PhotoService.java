package holymagic.vkpublicmanagement.service;

import holymagic.vkpublicmanagement.model.wall.attachment.Photo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static holymagic.vkpublicmanagement.model.ParameterizedTypeReferences.PHOTOS_RESPONSE_REF;

@Slf4j
@Service
@RequiredArgsConstructor
public class PhotoService {

    @Value("${get_wall_max_count}")
    private int maxCount;
    @Value("${get_from_wall_default_offset}")
    private int defaultOffset;

    private final ExchangeService exchangeService;

    public List<Photo> getWallAlbumPhotos(int count, int offset, boolean rev, String ownerId) {
        URI uri = exchangeService.provideGetWallAlbumUri(count, offset, rev, ownerId);
        return exchangeService.getData(uri, PHOTOS_RESPONSE_REF).getItems();
    }

    public Set<Long> getWallPhotoIds(int count, int offset, String ownerId) {
        Set<Long> photoIds = new LinkedHashSet<>((int) (1.25 * maxCount), 0.75f);
        List<Photo> albumPhotos = getWallAlbumPhotos(maxCount, offset, false, ownerId);
        extractPhotoIds(albumPhotos, photoIds);
        log.info("extracted album photo ids: {}", photoIds.size());
        return photoIds;
    }

    public Set<Long> getAllWallPhotoIds(String ownerId) throws InterruptedException {
        int offset = defaultOffset;
        Set<Long> photoIds = getWallPhotoIds(maxCount, offset, ownerId);
        int size = photoIds.size();
        offset += maxCount;
        int i = 1;
        while (size >= maxCount / 2) {
            if (i % 5 == 0) {
                Thread.sleep(500);
            }
            Set<Long> newPhotoIds = getWallPhotoIds(maxCount, offset, ownerId);
            photoIds.addAll(newPhotoIds);
            offset += maxCount;
            size = newPhotoIds.size();
            i++;
            log.info("current size is {} and offset is {}", photoIds.size(), offset);
        }
        log.info("got {} photoIds", photoIds.size());
        return photoIds;
    }

    private void extractPhotoIds(List<Photo> photos, Set<Long> photoIds) {
        for (Photo photo : photos) {
            photoIds.add(photo.getId());
        }
    }

}
