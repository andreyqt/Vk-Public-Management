package holymagic.vkpublicmanagement.mapper;

import holymagic.vkpublicmanagement.dto.CountResponseDto;
import holymagic.vkpublicmanagement.model.exec.CountResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CountResponseMapper {

    @Mappings({
            @Mapping(source = "firstTimestamp", target = "firstTimestamp", qualifiedByName = "mapLongToLocalDateTime"),
            @Mapping(source = "lastTimestamp", target = "lastTimestamp", qualifiedByName = "mapLongToLocalDateTime")
    })
    CountResponseDto toDto(CountResponse count);

    @Named("mapLongToLocalDateTime")
    default LocalDateTime mapLongToLocalDateTime(Long timestamp) {
        Instant instant = Instant.ofEpochSecond(timestamp);
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }

}
