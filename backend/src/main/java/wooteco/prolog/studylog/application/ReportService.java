package wooteco.prolog.studylog.application;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.studylog.application.dto.report.ReportAssembler;
import wooteco.prolog.studylog.application.dto.report.request.ReportRequest;
import wooteco.prolog.studylog.application.dto.report.response.ReportResponse;
import wooteco.prolog.studylog.application.report.ReportsRequestType;
import wooteco.prolog.studylog.domain.report.Report;
import wooteco.prolog.studylog.domain.repository.ReportRepository;

@Service
@Transactional(readOnly = true)
public class ReportService {

    private final ReportAssembler reportAssembler;
    private final ReportRepository reportRepository;
    private final List<ReportsRequestType> reportsRequestTypes;

    public ReportService(ReportAssembler reportAssembler,
                         ReportRepository reportRepository,
                         List<ReportsRequestType> reportsRequestTypes) {
        this.reportAssembler = reportAssembler;
        this.reportRepository = reportRepository;
        this.reportsRequestTypes = reportsRequestTypes;
    }

    @Transactional
    public ReportResponse createReport(ReportRequest reportRequest, Member member) {
        Report report = reportAssembler.of(reportRequest, member);
        Report savedReport = reportRepository.save(report);

        return reportAssembler.of(savedReport);
    }

    @Transactional
    public ReportResponse updateReport(ReportRequest reportRequest, Member member) {
        Report report = reportAssembler.of(reportRequest, member);
        Report savedReport = reportRepository.findById(report.getId())
            .orElseThrow(IllegalArgumentException::new);

        savedReport.update(report);

        return reportAssembler.of(savedReport);
    }

    public Object findReportsByUsername(String username, String type, Pageable pageable) {
        ReportsRequestType reportsRequest = reportsRequestTypes.stream()
            .filter(reportsRequestType -> reportsRequestType.isSupport(type))
            .findAny()
            .orElseThrow(IllegalArgumentException::new);

        return reportsRequest.execute(username, pageable);
    }

    public ReportResponse findReportById(Long reportId) {
        Report report = reportRepository.findById(reportId)
            .orElseThrow(IllegalArgumentException::new);

        return reportAssembler.of(report);
    }
}
