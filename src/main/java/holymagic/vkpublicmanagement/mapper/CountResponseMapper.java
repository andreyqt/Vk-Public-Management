package holymagic.vkpublicmanagement.mapper;

import holymagic.vkpublicmanagement.model.exec.CountResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CountResponseMapper {

    default void update(@MappingTarget CountResponse oldResponse, CountResponse newResponse) {
        oldResponse.setCount(oldResponse.getCount() + newResponse.getCount());
        oldResponse.setRequests(oldResponse.getRequests() + newResponse.getRequests());
        oldResponse.setOffset(newResponse.getOffset());
        oldResponse.setMessage(newResponse.getMessage());
    }

}
