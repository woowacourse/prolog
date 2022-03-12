package wooteco.prolog.report.application;


import static java.util.stream.Collectors.toMap;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wooteco.prolog.ability.application.AbilityService;
import wooteco.prolog.ability.application.StudylogAbilityService;
import wooteco.prolog.ability.domain.Ability;
import wooteco.prolog.ability.domain.StudylogAbility;
import wooteco.prolog.login.ui.LoginMember;
import wooteco.prolog.member.application.MemberService;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.report.application.dto.PageableResponse;
import wooteco.prolog.report.application.dto.ReportRequest;
import wooteco.prolog.report.application.dto.ReportResponse;
import wooteco.prolog.report.application.dto.ReportUpdateRequest;
import wooteco.prolog.report.domain.Report;
import wooteco.prolog.report.domain.ReportAbility;
import wooteco.prolog.report.domain.ReportAbilityStudylog;
import wooteco.prolog.report.domain.repository.ReportAbilityRepository;
import wooteco.prolog.report.domain.repository.ReportAbilityStudylogRepository;
import wooteco.prolog.report.domain.repository.ReportRepository;

@Service
@Transactional(readOnly = true)
public class ReportService {

    private ReportRepository reportRepository;
    private ReportAbilityRepository reportAbilityRepository;
    private ReportAbilityStudylogRepository reportAbilityStudylogRepository;
    private MemberService memberService;
    private AbilityService abilityService;
    private StudylogAbilityService studylogAbilityService;

    public ReportService(ReportRepository reportRepository,
                         ReportAbilityRepository reportAbilityRepository, ReportAbilityStudylogRepository reportAbilityStudylogRepository,
                         MemberService memberService,
                         AbilityService abilityService,
                         StudylogAbilityService studylogAbilityService) {
        this.reportRepository = reportRepository;
        this.reportAbilityRepository = reportAbilityRepository;
        this.reportAbilityStudylogRepository = reportAbilityStudylogRepository;
        this.memberService = memberService;
        this.abilityService = abilityService;
        this.studylogAbilityService = studylogAbilityService;
    }

    @Transactional
    public ReportResponse createReport(LoginMember loginMember, ReportRequest reportRequest) {
        Member member = memberService.findById(loginMember.getId());

        Report report = reportRepository.save(new Report(reportRequest.getTitle(), reportRequest.getDescription(), member));

        List<Long> abilityIds = reportRequest.getReportAbility().stream().map(it -> it.getAbilityId()).collect(Collectors.toList());

        List<Ability> abilities = abilityService.findByIdIn(loginMember.getId(), abilityIds);

        Map<Long, ReportAbility> reportAbilities = abilities.stream()
            .map(it -> createReportAbilities(report, reportRequest.findWeight(it.getId()), it))
            .collect(toMap(ReportAbility::getOriginAbilityId, Function.identity()));

        List<ReportAbility> persistReportAbilities = reportAbilityRepository.saveAll(reportAbilities.values());

        List<StudylogAbility> studylogAbilities = studylogAbilityService.findAbilityStudylogs(member.getUsername(), abilityIds);

        List<ReportAbilityStudylog> reportAbilityStudylogs = studylogAbilities.stream()
            .map(it -> new ReportAbilityStudylog(reportAbilities.get(it.getAbility().getId()), it.getStudylog(), it.getAbility().getId()))
            .collect(Collectors.toList());

        List<ReportAbilityStudylog> persistReportAbilityStudylogs = reportAbilityStudylogRepository.saveAll(reportAbilityStudylogs);

        return ReportResponse.of(report, persistReportAbilities, persistReportAbilityStudylogs);
    }

    private ReportAbility createReportAbilities(Report report, Integer weight, Ability ability) {
        ReportAbility parent = ability.getParent() != null
            ? createReportAbilities(report, null, ability.getParent())
            : null;

        return new ReportAbility(report, weight, ability.getId(), ability.getName(), ability.getDescription(), ability.getColor(), parent);
    }

    public PageableResponse<ReportResponse> findReportsByUsername(String username, Pageable pageable) {
        Member member = memberService.findByUsername(username);

        Page<Report> reports = reportRepository.findByMemberId(member.getId(), pageable);

        return new PageableResponse<>(ReportResponse.listOf(reports.getContent()), reports.getTotalElements(), reports.getTotalPages(), reports.getNumber() + 1);
    }

    @Transactional
    public ReportResponse updateReport(LoginMember loginMember, Long reportId, ReportUpdateRequest reportUpdateRequest) {
        Report report = reportRepository.findById(reportId).orElseThrow(IllegalArgumentException::new);
        if (!report.isBelongTo(loginMember.getId())) {
            throw new RuntimeException("내 리포트만 수정이 가능합니다.");
        }

        report.update(reportUpdateRequest.getTitle(), reportUpdateRequest.getDescription());

        return ReportResponse.of(report);
    }

    public void deleteReport(LoginMember loginMember, Long reportId) {
        Report report = reportRepository.findById(reportId).orElseThrow(IllegalArgumentException::new);
        if (!report.isBelongTo(loginMember.getId())) {
            throw new RuntimeException("내 리포트만 삭제가 가능합니다.");
        }

        reportRepository.deleteById(reportId);
    }
}
