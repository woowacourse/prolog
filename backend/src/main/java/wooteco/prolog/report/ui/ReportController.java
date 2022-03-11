package wooteco.prolog.report.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import wooteco.prolog.login.aop.MemberOnly;
import wooteco.prolog.login.domain.AuthMemberPrincipal;
import wooteco.prolog.login.ui.LoginMember;
import wooteco.prolog.report.application.ReportService;
import wooteco.prolog.report.application.dto.ReportRequest;
import wooteco.prolog.report.application.dto.ReportResponse;

@RestController
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }
//
//    @GetMapping("/reports")
//    public ResponseEntity<Object> findReportsById(
//        String username,
//        String type,
//        @PageableDefault(size = 20, direction = Direction.DESC, sort = "id") Pageable pageable
//    ) {
//        Object response = reportService.ㅊ(username, type, pageable);
//        return ResponseEntity.ok(response);
//    }

    //
//    @Deprecated
//    @GetMapping("/{username}/reports")
//    public ResponseEntity<Object> findReportsById_deprecated(
//            @PathVariable String username,
//            String type,
//            @PageableDefault(size = 20, direction = Direction.DESC, sort = "id") Pageable pageable
//    ) {
//        Object response = reportService.findReportsByUsername(username, type, pageable);
//        return ResponseEntity.ok(response);
//    }
//
//    @GetMapping("/reports/{reportId}")
//    public ResponseEntity<ReportResponse> findReportById(@PathVariable Long reportId) {
//        ReportResponse reportResponse = reportService.findReportById(reportId);
//        return ResponseEntity.ok(reportResponse);
//    }
//
    @MemberOnly
    @PostMapping("/reports")
    public ResponseEntity<ReportResponse> createReport(@AuthMemberPrincipal LoginMember member, @RequestBody ReportRequest reportRequest) {
        ReportResponse response = reportService.createReport(member, reportRequest);
        return ResponseEntity.ok().body(response);
    }
//
//    @MemberOnly
//    @PutMapping("/reports/{reportId}")
//    public ResponseEntity<ReportResponse> updateReport(
//        @PathVariable Long reportId,
//        @AuthMemberPrincipal LoginMember member,
//        @RequestBody ReportRequest2 reportRequest
//    ) {
//        ReportResponse reportResponse = reportService.updateReport(reportId, reportRequest, member);
//
//        return ResponseEntity.ok(reportResponse);
//    }
//
//    @MemberOnly
//    @DeleteMapping("/reports/{reportId}")
//    public ResponseEntity<Void> deleteReport(
//        @PathVariable Long reportId,
//        @AuthMemberPrincipal LoginMember member
//    ) {
//        reportService.deleteReport(reportId, member);
//
//        return ResponseEntity.ok().build();
//    }
}
