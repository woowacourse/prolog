package wooteco.prolog.studylog.application;

import static java.util.stream.Collectors.toList;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.studylog.application.dto.report.ReportAssembler;
import wooteco.prolog.studylog.application.dto.report.request.ReportRequest;
import wooteco.prolog.studylog.application.dto.report.request.abilitigraph.AbilityRequest;
import wooteco.prolog.studylog.application.dto.report.response.ReportResponse;
import wooteco.prolog.studylog.application.report.ReportsRequestType;
import wooteco.prolog.studylog.domain.report.Report;
import wooteco.prolog.studylog.domain.repository.AbilityRepository;
import wooteco.prolog.studylog.domain.repository.ReportRepository;

@Service
@Transactional(readOnly = true)
public class ReportService {

    private final ReportAssembler reportAssembler;
    private final ReportRepository reportRepository;
    private final AbilityRepository abilityRepository;
    private final List<ReportsRequestType> reportsRequestTypes;

    public ReportService(ReportAssembler reportAssembler,
                         ReportRepository reportRepository,
                         AbilityRepository abilityRepository,
                         List<ReportsRequestType> reportsRequestTypes) {
        this.reportAssembler = reportAssembler;
        this.reportRepository = reportRepository;
        this.abilityRepository = abilityRepository;
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

        verifyGraphAbilitiesAreParent(reportRequest);

        savedReport.update(report);

        return reportAssembler.of(savedReport);
    }

    private void verifyGraphAbilitiesAreParent(ReportRequest reportRequest) {
        List<Long> abilityIds = reportRequest.getAbilityGraph().getAbilities().stream()
            .map(AbilityRequest::getId)
            .distinct()
            .collect(toList());

        Long count = abilityRepository.countParentAbilitiesOf(abilityIds);

        if(count != abilityIds.size()) {
            throw new IllegalArgumentException();
        }
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
