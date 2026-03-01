package holymagic.vkpublicmanagement.mapper;

import holymagic.vkpublicmanagement.model.exec.CountResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class CountResponseMapperTest {

    private CountResponseMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = Mappers.getMapper(CountResponseMapper.class);
    }

    @Test
    public void updateTest() {
        CountResponse oldResponse = new CountResponse(10, 5, 100, "oldMsg");
        CountResponse newResponse = new CountResponse(10, 5, 200, "newMsg");
        mapper.update(oldResponse, newResponse);
        assertEquals(20, oldResponse.getCount());
        assertEquals(10, oldResponse.getRequests());
        assertEquals(200, oldResponse.getOffset());
        assertEquals("newMsg", oldResponse.getMessage());
    }

}
