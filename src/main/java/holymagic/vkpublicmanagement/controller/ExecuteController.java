package holymagic.vkpublicmanagement.controller;

import holymagic.vkpublicmanagement.dto.CountResponseDto;
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

    @GetMapping("/count")
    public ResponseEntity<CountResponseDto> getCount(
            @RequestParam @NotBlank(message = "query can't be empty") String query,
            @RequestParam(required = false, defaultValue = "place_4_rest") String domain,
            @RequestParam(required = false, defaultValue = "0") int offset
    ) {
        wallParamValidator.validateOffset(offset);
        return ResponseEntity.ok(executeService.getCount(query, domain, offset));
    }

}
