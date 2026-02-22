package holymagic.vkpublicmanagement.controller;

import holymagic.vkpublicmanagement.dto.CountResponseDto;
import holymagic.vkpublicmanagement.mapper.CountResponseMapper;
import holymagic.vkpublicmanagement.model.exec.CountResponse;
import holymagic.vkpublicmanagement.service.ExecuteService;
import holymagic.vkpublicmanagement.validator.WallParamValidator;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/exec")
public class ExecuteController {

    private final ExecuteService executeService;
    private final WallParamValidator wallParamValidator;
    private final CountResponseMapper countResponseMapper;

    @GetMapping("/count")
    public ResponseEntity<CountResponseDto> getCount(
            @RequestParam @NotBlank(message = "query can't be empty") String query,
            @RequestParam(required = false, defaultValue = "place_4_rest") String domain,
            @RequestParam(required = false, defaultValue = "0") int offset
    ) {
        wallParamValidator.validateOffset(offset);
        CountResponse response = executeService.getCount(query, domain, offset);
        return ResponseEntity.ok(countResponseMapper.toDto(response));
    }

    @GetMapping("/count/total")
    public ResponseEntity<CountResponseDto> geTotalCount(
            @RequestParam @NotBlank(message = "query can't be empty") String query,
            @RequestParam(required = false, defaultValue = "place_4_rest") String domain
    ) {
        CountResponse response = executeService.getTotalCount(query, domain);
        return ResponseEntity.ok(countResponseMapper.toDto(response));
    }
}
