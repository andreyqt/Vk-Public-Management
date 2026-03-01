package holymagic.vkpublicmanagement.controller;

import holymagic.vkpublicmanagement.model.exec.CountResponse;
import holymagic.vkpublicmanagement.service.ExecuteService;
import holymagic.vkpublicmanagement.validator.ExecParamValidator;
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
    private final ExecParamValidator execParamValidator;
    private final WallParamValidator wallParamValidator;

    @GetMapping("/count")
    public ResponseEntity<CountResponse> getCount(
            @RequestParam @NotBlank(message = "query can't be empty") String query,
            @RequestParam(required = false, defaultValue = "place_4_rest") String domain,
            @RequestParam(required = false, defaultValue = "0") int offset
    ) {
        wallParamValidator.validateOffset(offset);
        CountResponse response = executeService.getCount(query, domain, offset);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/count/period")
    public ResponseEntity<CountResponse> getCountOverPeriod(
            @RequestParam long beginTimestamp,
            @RequestParam long endTimestamp,
            @RequestParam @NotBlank(message = "query can't be empty") String query,
            @RequestParam(required = false, defaultValue = "place_4_rest") String domain,
            @RequestParam(required = false, defaultValue = "0") int offset
    ) {
        wallParamValidator.validateOffset(offset);
        execParamValidator.validateGetCountTimestamps(beginTimestamp, endTimestamp);
        CountResponse response = executeService.getCountOverPeriod(beginTimestamp, endTimestamp,
                query, domain, offset);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/count/all")
    public ResponseEntity<CountResponse> getAllCount(
            @RequestParam long beginTimestamp,
            @RequestParam long endTimestamp,
            @RequestParam(required = false, defaultValue = "place_4_rest") String domain,
            @RequestParam(required = false, defaultValue = "0") int offset
    ) {
        wallParamValidator.validateOffset(offset);
        execParamValidator.validateGetCountTimestamps(beginTimestamp, endTimestamp);
        CountResponse response = executeService.getFullCount(beginTimestamp, endTimestamp, domain, offset);
        return ResponseEntity.ok(response);
    }

}
