package wooteco.prolog.report.ui;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import wooteco.prolog.login.domain.AuthMemberPrincipal;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.report.application.ReportService;
import wooteco.prolog.report.application.dto.report.request.ReportRequest;
import wooteco.prolog.report.application.dto.report.response.ReportResponse;

@RestController
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/reports")
    public ResponseEntity<Object> findReportsById(
        String member,
        String type,
        @PageableDefault(size = 20, direction = Direction.DESC, sort = "id") Pageable pageable
    ) {
        Object response = reportService.findReportsByUsername(member, type, pageable);
        return ResponseEntity.ok(response);
    }

    @Deprecated
    @GetMapping("/{username}/reports")
    public ResponseEntity<Object> findReportsById_deprecated(
            @PathVariable String username,
            String type,
            @PageableDefault(size = 20, direction = Direction.DESC, sort = "id") Pageable pageable
    ) {
        Object response = reportService.findReportsByUsername(username, type, pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/reports/{reportId}")
    public ResponseEntity<ReportResponse> findReportById(
        @PathVariable Long reportId
    ) {
        ReportResponse reportResponse = reportService.findReportById(reportId);
        return ResponseEntity.ok(reportResponse);
    }

    @PostMapping("/reports")
    public ResponseEntity<ReportResponse> createReport(
        @AuthMemberPrincipal Member member,
        @RequestBody ReportRequest reportRequest
    ) {
        ReportResponse reportResponse = reportService.createReport(reportRequest, member);

        return ResponseEntity.ok(reportResponse);
    }

    @PutMapping("/reports")
    public ResponseEntity<ReportResponse> updateReport(
        @AuthMemberPrincipal Member member,
        @RequestBody ReportRequest reportRequest
    ) {
        ReportResponse reportResponse = reportService.updateReport(reportRequest, member);

        return ResponseEntity.ok(reportResponse);
    }
}
