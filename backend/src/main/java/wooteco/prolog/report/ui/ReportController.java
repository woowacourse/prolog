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
import wooteco.prolog.report.application.ReportService;
import wooteco.prolog.report.application.dto.PageableResponse;
import wooteco.prolog.report.application.dto.ReportRequest;
import wooteco.prolog.report.application.dto.ReportResponse;
import wooteco.prolog.report.application.dto.ReportUpdateRequest;

@RestController
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @MemberOnly
    @PostMapping("/reports")
    public ResponseEntity<ReportResponse> createReport(@AuthMemberPrincipal LoginMember member, @RequestBody ReportRequest reportRequest) {
        ReportResponse response = reportService.createReport(member, reportRequest);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/members/{username}/reports")
    public ResponseEntity<PageableResponse<ReportResponse>> findReports(@PathVariable String username,
                                                                        @PageableDefault(size = 20, direction = Direction.DESC, sort = "id") Pageable pageable) {
        PageableResponse<ReportResponse> response = reportService.findReportsByUsername(username, pageable);
        return ResponseEntity.ok(response);
    }

    @MemberOnly
    @PutMapping("/reports/{reportId}")
    public ResponseEntity<ReportResponse> updateReport(@AuthMemberPrincipal LoginMember member, @PathVariable Long reportId, @RequestBody ReportUpdateRequest reportUpdateRequest) {
        ReportResponse reportResponse = reportService.updateReport(member, reportId, reportUpdateRequest);

        return ResponseEntity.ok(reportResponse);
    }

    @MemberOnly
    @DeleteMapping("/reports/{reportId}")
    public ResponseEntity<Void> deleteReport(@AuthMemberPrincipal LoginMember member, @PathVariable Long reportId) {
        reportService.deleteReport(member, reportId);

        return ResponseEntity.noContent().build();
    }
}
