package wooteco.prolog.report.application;


import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wooteco.prolog.ability.application.AbilityService;
import wooteco.prolog.ability.application.StudylogAbilityService;
import wooteco.prolog.ability.application.dto.HierarchyAbilityResponse;
import wooteco.prolog.ability.domain.StudylogAbility;
import wooteco.prolog.common.PageableResponse;
import wooteco.prolog.login.ui.LoginMember;
import wooteco.prolog.member.application.MemberService;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.report.application.dto.ReportRequest;
import wooteco.prolog.report.application.dto.ReportResponse;
import wooteco.prolog.report.application.dto.ReportUpdateRequest;
import wooteco.prolog.report.domain.Report;
import wooteco.prolog.report.domain.ReportAbility;
import wooteco.prolog.report.domain.ReportStudylog;
import wooteco.prolog.report.domain.repository.ReportAbilityRepository;
import wooteco.prolog.report.domain.repository.ReportRepository;
import wooteco.prolog.report.domain.repository.ReportStudylogRepository;

@Service
@Transactional(readOnly = true)
public class ReportService {

    private ReportRepository reportRepository;
    private ReportAbilityRepository reportAbilityRepository;
    private ReportStudylogRepository reportStudylogRepository;
    private MemberService memberService;
    private AbilityService abilityService;
    private StudylogAbilityService studylogAbilityService;

    public ReportService(ReportRepository reportRepository, ReportAbilityRepository reportAbilityRepository, ReportStudylogRepository reportStudylogRepository,
                         MemberService memberService, AbilityService abilityService, StudylogAbilityService studylogAbilityService) {
        this.reportRepository = reportRepository;
        this.reportAbilityRepository = reportAbilityRepository;
        this.reportStudylogRepository = reportStudylogRepository;
        this.memberService = memberService;
        this.abilityService = abilityService;
        this.studylogAbilityService = studylogAbilityService;
    }

    @Transactional
    public ReportResponse createReport(LoginMember loginMember, ReportRequest reportRequest) {
        Report report = reportRepository.save(
            new Report(
                reportRequest.getTitle(),
                reportRequest.getDescription(),
                loginMember.getId(),
                LocalDate.parse(reportRequest.getStartDate()),
                LocalDate.parse(reportRequest.getEndDate())
            )
        );

        List<HierarchyAbilityResponse> abilities = abilityService.findParentAbilitiesByMemberId(loginMember.getId());
        List<ReportAbility> reportAbilities = reportAbilityRepository.saveAll(abilities.stream()
            .map(it -> new ReportAbility(it.getName(), it.getDescription(), it.getColor(), reportRequest.findWeight(it.getId()), it.getId(), report.getId()))
            .collect(Collectors.toList()));

        List<StudylogAbility> studylogAbilities = studylogAbilityService
            .findStudylogAbilitiesInPeriod(loginMember.getId(), LocalDate.parse(reportRequest.getStartDate()), LocalDate.parse(reportRequest.getEndDate()));

        List<ReportStudylog> reportStudylogs = reportStudylogRepository.saveAll(studylogAbilities.stream()
            .map(it -> new ReportStudylog(report.getId(), findReportAbilityByAbility(it.getAbility().getId(), reportAbilities), it.getStudylog()))
            .collect(Collectors.toList()));

        return ReportResponse.of(report, reportAbilities, reportStudylogs);

    }

    private ReportAbility findReportAbilityByAbility(Long abilityId, List<ReportAbility> reportAbilities) {
        return reportAbilities.stream()
            .filter(it -> it.getId().equals(abilityId))
            .findFirst()
            .orElseThrow(RuntimeException::new);
    }

    public ReportResponse findReportById(Long reportId) {
        Report persistReport = reportRepository.findById(reportId).orElseThrow(IllegalArgumentException::new);

        List<ReportAbility> persistReportAbilities = reportAbilityRepository.findByReportId(persistReport.getId());
        List<ReportStudylog> persistReportStudylogs = reportStudylogRepository.findByReportId(persistReport.getId());

        return ReportResponse.of(persistReport, persistReportAbilities, persistReportStudylogs);
    }

    public PageableResponse<ReportResponse> findReportsByUsername(String username, Pageable pageable) {
        Member member = memberService.findByUsername(username);
        Page<Report> reports = reportRepository.findByMemberId(member.getId(), pageable);

        List<ReportResponse> reportResponses = ReportResponse.listOf(reports);

        return PageableResponse.of(reportResponses, reports);
    }

    @Transactional
    public void updateReport(LoginMember loginMember, Long reportId, ReportUpdateRequest reportUpdateRequest) {
        Report report = reportRepository.findById(reportId).orElseThrow(IllegalArgumentException::new);
        if (!report.isBelongTo(loginMember.getId())) {
            throw new RuntimeException("내 리포트만 수정이 가능합니다.");
        }

        report.update(reportUpdateRequest.getTitle(), reportUpdateRequest.getDescription());
    }

    public void deleteReport(LoginMember loginMember, Long reportId) {
        Report report = reportRepository.findById(reportId).orElseThrow(IllegalArgumentException::new);
        if (!report.isBelongTo(loginMember.getId())) {
            throw new RuntimeException("내 리포트만 삭제가 가능합니다.");
        }

        reportRepository.deleteById(reportId);
    }
}
