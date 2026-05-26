package holymagic.vkpublicmanagement.service;

import holymagic.vkpublicmanagement.exception.EmptyResponseException;
import holymagic.vkpublicmanagement.exception.ResponseOverflowException;
import holymagic.vkpublicmanagement.model.wall.Post;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
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
            validateSize(result);
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

    private void validateResponse(List<Post> posts) {
        log.info("Received {} posts", posts.size());
        if (posts.isEmpty()) {
            throw new EmptyResponseException("Received empty response from server");
        }
        if (posts.getFirst().getIsDeleted() != null && posts.getFirst().getIsDeleted()) {
            throw new EmptyResponseException("Post was deleted");
        }
    }

    private void validateSize(List<Post> posts) {
        if (posts.size() > maxSize) {
            throw new ResponseOverflowException("too many posts");
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

}
