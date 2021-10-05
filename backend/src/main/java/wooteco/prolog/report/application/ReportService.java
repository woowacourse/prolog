package wooteco.prolog.report.application;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Objects;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.report.application.dto.report.ReportAssembler;
import wooteco.prolog.report.application.dto.report.request.ReportRequest;
import wooteco.prolog.report.application.dto.report.request.abilitigraph.AbilityRequest;
import wooteco.prolog.report.application.dto.report.response.ReportResponse;
import wooteco.prolog.report.application.report.ReportsRequestType;
import wooteco.prolog.report.domain.report.Report;
import wooteco.prolog.report.domain.ablity.repository.AbilityRepository;
import wooteco.prolog.report.domain.report.repository.ReportRepository;

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
        Report updateSourceReport = reportAssembler.of(reportRequest, member);
        Report savedReport = reportRepository.findById(updateSourceReport.getId())
            .orElseThrow(IllegalArgumentException::new);

        verifyIsAllowedUser(member, savedReport);
        verifyGraphAbilitiesAreParent(reportRequest);
        checkIsRepresent(member, updateSourceReport);

        savedReport.update(updateSourceReport);

        return reportAssembler.of(savedReport);
    }

    private void verifyIsAllowedUser(Member member, Report savedReport) {
        if(!Objects.equals(savedReport.getMember(), member)) {
            throw new IllegalArgumentException();
        }
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

    private void checkIsRepresent(Member member, Report updateSourceReport) {
        if(updateSourceReport.isRepresent()) {
            reportRepository.findRepresentReportOf(member.getUsername())
                    .ifPresent(Report::toUnRepresent);
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
