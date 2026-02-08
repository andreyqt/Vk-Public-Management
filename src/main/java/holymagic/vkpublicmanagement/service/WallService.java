package holymagic.vkpublicmanagement.service;

import holymagic.vkpublicmanagement.exception.EmptyResponseException;
import holymagic.vkpublicmanagement.model.wall.Post;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.List;

import static holymagic.vkpublicmanagement.model.ParameterizedTypeReferences.POST_RESPONSE_REF;
import static holymagic.vkpublicmanagement.model.ParameterizedTypeReferences.WALL_RESPONSE_REF;

@Slf4j
@Service
@RequiredArgsConstructor
public class WallService {

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

    public List<Post> searchPostsOnWall(String query, int count, int offset) {
        URI uri = exchangeService.provideWallSearchUri(query, count, offset);
        List<Post> response = exchangeService.getData(uri, POST_RESPONSE_REF).getItems();
        validateResponse(response);
        return response;
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

}
