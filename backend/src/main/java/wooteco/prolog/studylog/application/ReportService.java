package wooteco.prolog.studylog.application;

import static java.util.stream.Collectors.toList;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.studylog.application.dto.report.ReportResponse;
import wooteco.prolog.studylog.domain.report.Report;
import wooteco.prolog.studylog.domain.repository.ReportRepository;

@Service
public class ReportService {

    private final ReportRepository reportRepository;

    public ReportService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    public List<ReportResponse> findReportsByMember(Member member, Pageable pageable) {
        List<Report> reports = reportRepository.findReportsByMember(member, pageable);

        return reports.stream()
            .map(ReportResponse::from)
            .collect(toList());
    }

}
