package wooteco.prolog.report.ui;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import wooteco.prolog.login.aop.MemberOnly;
import wooteco.prolog.login.domain.AuthMemberPrincipal;
import wooteco.prolog.login.ui.LoginMember;
import wooteco.prolog.report.application.ReportService2;
import wooteco.prolog.report.application.dto.request.ReportRequest2;
import wooteco.prolog.report.application.dto.response.ReportResponse;

@RestController
public class ReportController {

    private final ReportService2 reportService;

    public ReportController(ReportService2 reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/reports")
    public ResponseEntity<Object> findReportsById(
        String username,
        String type,
        @PageableDefault(size = 20, direction = Direction.DESC, sort = "id") Pageable pageable
    ) {
        Object response = reportService.findReportsByUsername(username, type, pageable);
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
    public ResponseEntity<ReportResponse> findReportById(@PathVariable Long reportId) {
        ReportResponse reportResponse = reportService.findReportById(reportId);
        return ResponseEntity.ok(reportResponse);
    }

    @MemberOnly
    @PostMapping("/reports")
    public ResponseEntity<ReportResponse> createReport(@AuthMemberPrincipal LoginMember member, @RequestBody ReportRequest2 reportRequest) {
        ReportResponse reportResponse = reportService.createReport(reportRequest, member);
        return ResponseEntity.ok(reportResponse);
    }

    @MemberOnly
    @PutMapping("/reports/{reportId}")
    public ResponseEntity<ReportResponse> updateReport(
        @PathVariable Long reportId,
        @AuthMemberPrincipal LoginMember member,
        @RequestBody ReportRequest2 reportRequest
    ) {
        ReportResponse reportResponse = reportService.updateReport(reportId, reportRequest, member);

        return ResponseEntity.ok(reportResponse);
    }

    @MemberOnly
    @DeleteMapping("/reports/{reportId}")
    public ResponseEntity<Void> deleteReport(
        @PathVariable Long reportId,
        @AuthMemberPrincipal LoginMember member
    ) {
        reportService.deleteReport(reportId, member);

        return ResponseEntity.ok().build();
    }
}
