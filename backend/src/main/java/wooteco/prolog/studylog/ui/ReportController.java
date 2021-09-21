package wooteco.prolog.studylog.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import wooteco.prolog.login.domain.AuthMemberPrincipal;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.studylog.application.ReportService;
import wooteco.prolog.studylog.application.dto.report.request.ReportRequest;
import wooteco.prolog.studylog.application.dto.report.response.ReportResponse;

@RestController
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
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
