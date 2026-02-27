package holymagic.vkpublicmanagement.controller;

import holymagic.vkpublicmanagement.model.exec.CountResponse;
import holymagic.vkpublicmanagement.service.ExecuteService;
import holymagic.vkpublicmanagement.validator.ExecParamValidator;
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

    @GetMapping("/count")
    public ResponseEntity<CountResponse> getCount(
            @RequestParam @NotBlank(message = "query can't be empty") String query,
            @RequestParam(required = false, defaultValue = "place_4_rest") String domain
    ) {
        CountResponse response = executeService.getCount(query, domain);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/count/period")
    public ResponseEntity<CountResponse> getCountOverPeriod(
            @RequestParam long beginTimestamp,
            @RequestParam long endTimestamp,
            @RequestParam @NotBlank(message = "query can't be empty") String query,
            @RequestParam(required = false, defaultValue = "place_4_rest") String domain
    ) {
        execParamValidator.validateGetCountTimestamps(beginTimestamp, endTimestamp);
        CountResponse response = executeService.getCountOverPeriod(beginTimestamp, endTimestamp, query, domain);
        return ResponseEntity.ok(response);
    }

}
