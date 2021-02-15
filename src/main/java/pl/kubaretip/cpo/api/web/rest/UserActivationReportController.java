package pl.kubaretip.cpo.api.web.rest;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.kubaretip.cpo.api.service.UserActivationReportService;

@RestController
@RequestMapping("/user-activation-reports")
public class UserActivationReportController {

    private final UserActivationReportService userActivationReportService;

    public UserActivationReportController(UserActivationReportService userActivationReportService) {
        this.userActivationReportService = userActivationReportService;
    }

    @GetMapping(params = {"user_id"})
    public ResponseEntity<Resource> getUserActivationReport(@RequestParam("user_id") long userId) {
        var userActivationReport = userActivationReportService.getUserActivationReport(userId);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + userActivationReport.getName() + "\"")
                .body(new ByteArrayResource(userActivationReport.getData()));
    }


}
