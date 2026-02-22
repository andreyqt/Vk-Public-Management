package holymagic.vkpublicmanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CountResponseDto {
    private int count;
    private LocalDateTime firstTimestamp;
    private LocalDateTime lastTimestamp;
}
