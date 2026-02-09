package holymagic.vkpublicmanagement.model.user;

import java.net.URI;

public record AuthUri(String message, URI uri) {

    public AuthUri(URI uri) {
        this("follow the link, login and exchange the new link", uri);
    }

}
