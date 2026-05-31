package holymagic.vkpublicmanagement.service;

import holymagic.vkpublicmanagement.exception.EmptyResponseException;
import holymagic.vkpublicmanagement.model.wall.Post;
import holymagic.vkpublicmanagement.model.wall.attachment.Attachment;
import holymagic.vkpublicmanagement.model.wall.attachment.Photo;
import holymagic.vkpublicmanagement.model.wall.attachment.PhotoAttachment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static holymagic.vkpublicmanagement.model.ParameterizedTypeReferences.POST_RESPONSE_REF;
import static holymagic.vkpublicmanagement.model.ParameterizedTypeReferences.WALL_RESPONSE_REF;

@Slf4j
@Service
@RequiredArgsConstructor
public class WallService {

    @Value("${get_wall_max_count}")
    private int maxCount;
    @Value("${search_wall_max_size}")
    private int maxSize;
    @Value("${get_from_wall_default_offset}")
    private int defaultOffset;

    private final ExchangeService exchangeService;

    public List<Post> getPostsFromWall(int count, int offset) {
        URI uri = exchangeService.provideGetFromWallUri(count, offset);
        List<Post> posts = exchangeService.getData(uri, WALL_RESPONSE_REF).getItems();
        validateResponse(posts);
        return posts;
    }

    public Post getPostById(String id) {
        URI uri = exchangeService.provideGetByIdUri(id);
        List<Post> response = exchangeService.getData(uri, POST_RESPONSE_REF).getItems();
        validateResponse(response);
        return response.getFirst();
    }

    public Map<Long, String> getPostsWithNoLikes(int count, int offset) {
        Map<Long, String> result = new HashMap<>();
        List<Post> response = getPostsFromWall(maxCount, offset);
        extractPostsWithNoLikes(response, result);
        while (result.size() < count || response.size() < maxCount) {
            offset += 100;
            response = getPostsFromWall(maxCount, offset);
            extractPostsWithNoLikes(response, result);
        }
        return result;
    }

    public List<Post> searchPostsOnWall(String query, int count, int offset) {
        URI uri = exchangeService.provideWallSearchUri(query, count, offset);
        List<Post> response = exchangeService.getData(uri, POST_RESPONSE_REF).getItems();
        validateResponse(response);
        return response;
    }

    public List<Post> searchAllPostsOnWall(String query) {
        int offset = defaultOffset;
        List<Post> response = searchPostsOnWall(query, maxCount, offset);
        List<Post> result = new ArrayList<>(response);
        while (response.size() == 100) {
            offset += 100;
            response = searchPostsOnWall(query, maxCount, offset);
            result.addAll(response);
            if (result.size() > maxSize) {
                log.info("capacity limit reached: {}", maxCount);
                break;
            }
        }
        log.info("received all posts for query: {} \n total size: {}", query, result.size());
        return result;
    }

    public List<String> getHashtagsFromWall(String query) {
        int offset = defaultOffset;
        List<Post> response = searchPostsOnWall(query, maxCount, offset);
        Set<String> result = extractHashtagsFromPost(response);
        while (response.size() == 100) {
            offset += 100;
            response = searchPostsOnWall(query, maxCount, offset);
            result.addAll(extractHashtagsFromPost(response));
        }
        log.info("received all tags for hashtag: {} \n total size: {}", query, result.size());
        return new ArrayList<>(result);
    }

    public Set<Long> getPhotoIds(int count, int offset) {
        Set<Long> photoIds = new LinkedHashSet<>();
        List<Post> posts = getPostsFromWall(count, offset);
        extractPhotoIdsFromPosts(posts, photoIds);
        log.info("got {} photosIds", photoIds.size());
        return photoIds;
    }

    public Set<Long> getAllPhotoIds() {
        int offset = defaultOffset;
        Set<Long> photoIds = getPhotoIds(maxCount, offset);
        int size = photoIds.size();
        offset += maxCount;
        while (size >= maxCount / 2) {
            Set<Long> newPhotoIds = getPhotoIds(maxCount, offset);
            photoIds.addAll(newPhotoIds);
            offset += maxCount;
            size = newPhotoIds.size();
            log.info("current size is {} and offset is {}", photoIds.size(), offset);
        }
        log.info("got {} photoIds", photoIds.size());
        return photoIds;
    }

    private void validateResponse(List<Post> posts) {
        if (posts == null) {
            throw new EmptyResponseException("received empty response");
        }
        log.info("Received {} posts", posts.size());
        if (posts.isEmpty()) return;
        if (posts.getFirst().getIsDeleted() != null && posts.getFirst().getIsDeleted()) {
            throw new EmptyResponseException("Post was deleted");
        }
    }

    private void extractPostsWithNoLikes(List<Post> posts, Map<Long, String> result) {
        for (Post post : posts) {
            if (post.getLikes().getCount() == 0) {
                result.put(post.getId(), post.getText());
            }
        }
        log.info("extracted post ids, current size is {}", result.size());
    }

    private Set<String> extractHashtagsFromPost(List<Post> posts) {
        Set<String> hashtags = new HashSet<>();
        for (Post post : posts) {
            extractHashtagsFromString(post.getText(), hashtags);
        }
        return hashtags;
    }

    private void extractHashtagsFromString(String text, Set<String> hashtags) {
        for (String hashtag : text.split(" ")) {
            if (hashtag.startsWith("#")) {
                String[] tags = hashtag.split("\n");
                for (String tag : tags) {
                    if (tag.startsWith("#")) {
                        hashtags.add(tag.toLowerCase());
                    }
                }
            }
        }
    }

    private void extractPhotoIdsFromPosts(List<Post> posts, Set<Long> photoIds) {
        for (Post post : posts) {
            List<Attachment> attachments = post.getAttachments();
            for (Attachment attachment : attachments) {
                if (attachment instanceof PhotoAttachment photoAttachment) {
                    Photo photo = photoAttachment.getPhoto();
                    photoIds.add(photo.getId());
                }
            }
        }
    }

}
