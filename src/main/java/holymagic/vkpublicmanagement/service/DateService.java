package holymagic.vkpublicmanagement.service;

import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
public class DateService {

    public LocalDateTime toLocalDateTime(Long unixTimestamp) {
        Instant instant = Instant.ofEpochMilli(unixTimestamp*1000L);
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }

}
