package holymagic.vkpublicmanagement.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class DateServiceTest {

    private DateService dateService;

    @BeforeEach
    void setUp() {
        dateService = new DateService();
    }

    @Test
    public void toLocalDateTimeTest() {
        Long firstUnixTimestamp = 1771785000L;
        Long secondUnixTimestamp = 1733558400L;
        LocalDateTime expectedFirstDate = LocalDateTime.of(2026,2,22,21,30);
        LocalDateTime expectedSecondDate = LocalDateTime.of(2024,12,7,11,0);
        LocalDateTime actualFirstDate = dateService.toLocalDateTime(firstUnixTimestamp);
        LocalDateTime actualSecondDate = dateService.toLocalDateTime(secondUnixTimestamp);
        assertEquals(expectedFirstDate, actualFirstDate);
        assertEquals(expectedSecondDate, actualSecondDate);
    }

}
