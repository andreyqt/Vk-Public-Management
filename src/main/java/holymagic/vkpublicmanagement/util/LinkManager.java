package holymagic.vkpublicmanagement.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class LinkManager {

    @Value("${my_public_owner_id}")
    private String ownerId;
    @Value("${uri_photo_path}")
    private String photoPath;
    @Value("${saved_photo_path}")
    private String savedPhotoPath;
    @Value("${init_capacity}")
    private int initCapacity;
    @Value("${load_factor}")
    private float loadFactor;

    public String createPhotoLink(Long photoId, String ownerId) {
        return photoPath + ownerId + "_" + photoId;
    }

    public String createPhotoLink(Long photoId) {
        return createPhotoLink(photoId, ownerId);
    }

    public List<String> createPhotoLinks(Set<Long> photoIds) {
        return photoIds.stream()
                       .map(this::createPhotoLink)
                       .collect(Collectors.toList());
    }

    public void savePhotoLinkToFile(String link) {
        saveLinksToFile(List.of(link), savedPhotoPath);
    }

    public void saveLinksToFile(List<String> links, String path) {
       validatePhotoLinks(links);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path, true))) {
            for (String link : links) {
                bw.write(link);
                bw.newLine();
            }
            log.info("saved {} links to file {}", links.size(), savedPhotoPath);
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException("Failed to save link to file: " + e.getMessage());
        }
    }

    public void saveLinksToFile(List<String> links) {
        saveLinksToFile(links, savedPhotoPath);
    }

    public void saveLinksToFile(Set<Long> ids, String path) {
        List<String> stringLinks = ids.stream()
                .map(String::valueOf)
                .collect(Collectors.toList());
        saveLinksToFile(stringLinks, path);
    }

    public void saveLinksToFile(Set<Long> ids) {
        saveLinksToFile(ids, savedPhotoPath);
    }

    public Set<Long> readIdsFromFile(String path) {
        Set<Long> ids = new HashSet<>(initCapacity, loadFactor);
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    try {
                        ids.add(Long.parseLong(line.trim()));
                    } catch (NumberFormatException e) {
                        log.warn("couldn't parse id: {}", line);
                    }
                }
            }
        } catch (IOException e) {
            log.error("couldn't read from file: {}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
        log.info("read {} ids from file: {}", ids.size(), path);
        return ids;
    }

    private void validatePhotoLinks(List<String> links) {
        if (links == null || links.isEmpty()) {
            throw new IllegalArgumentException("link(s) is null or empty");
        }
    }

}
