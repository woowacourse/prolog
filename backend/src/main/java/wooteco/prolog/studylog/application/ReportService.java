package wooteco.prolog.studylog.application;

import static java.util.stream.Collectors.toList;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.studylog.application.dto.report.ReportAssembler;
import wooteco.prolog.studylog.application.dto.report.request.ReportRequest;
import wooteco.prolog.studylog.application.dto.report.response.ReportResponse;
import wooteco.prolog.studylog.domain.report.Report;
import wooteco.prolog.studylog.domain.repository.ReportRepository;

@Service
@Transactional(readOnly = true)
public class ReportService {

    private final ReportAssembler reportAssembler;
    private final ReportRepository reportRepository;

    public ReportService(ReportAssembler reportAssembler, ReportRepository reportRepository) {
        this.reportAssembler = reportAssembler;
        this.reportRepository = reportRepository;
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

    public List<ReportResponse> findReportsByMember(Member member, Pageable pageable) {
        List<Report> reports = reportRepository.findReportsByMember(member, pageable);

        return reports.stream()
            .map(ReportResponse::from)
            .collect(toList());
    }
}
