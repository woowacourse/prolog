package wooteco.prolog.report.application;


import static java.util.stream.Collectors.toMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wooteco.prolog.ability.application.AbilityService;
import wooteco.prolog.ability.application.StudylogAbilityService;
import wooteco.prolog.ability.domain.Ability;
import wooteco.prolog.ability.domain.StudylogAbility;
import wooteco.prolog.login.ui.LoginMember;
import wooteco.prolog.member.application.MemberService;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.report.application.dto.ReportRequest;
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

    public ReportService(ReportRepository reportRepository, ReportAbilityRepository reportAbilityRepository,
                         ReportAbilityStudylogRepository reportAbilityStudylogRepository, MemberService memberService,
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
    public void createReport(LoginMember loginMember, ReportRequest reportRequest) {
        Report report = reportRepository.save(new Report(reportRequest.getTitle(), reportRequest.getDescription()));
        Map<Long, Float> reportRequestMap = reportRequest.getReportAbility().stream()
            .collect(toMap(it -> it.getAbilityId(), it -> it.getWeight()));

        List<Long> abilityIds = new ArrayList<>(reportRequestMap.keySet());
        List<Ability> abilities = abilityService.findByIdIn(loginMember.getId(), abilityIds);

        Map<Long, ReportAbility> reportAbilities = abilities.stream()
            .map(it -> createReportAbilities(report, it, reportRequestMap.get(it.getId())))
            .collect(toMap(it -> it.getOriginAbilityId(), Function.identity()));

        reportAbilityRepository.saveAll(reportAbilities.values());

        Member member = memberService.findById(loginMember.getId());
        List<StudylogAbility> studylogAbilities = studylogAbilityService.findAbilityStudylogs(member.getUsername(), abilityIds);

        List<ReportAbilityStudylog> reportAbilityStudylogs = studylogAbilities.stream()
            .map(it -> new ReportAbilityStudylog(reportAbilities.get(it.getAbility().getId()), it.getStudylog(), it.getAbility().getId()))
            .collect(Collectors.toList());

        reportAbilityStudylogRepository.saveAll(reportAbilityStudylogs);
    }

    private ReportAbility createReportAbilities(Report report, Ability ability, Float weight) {
        if (ability.isParent()) {
            return new ReportAbility(report, weight, ability.getId(), ability.getName(), ability.getDescription(), ability.getColor(),
                createReportAbilities(report, ability.getParent(), null));
        }
        return new ReportAbility(report, weight, ability.getId(), ability.getName(), ability.getDescription(), ability.getColor(), null);
    }
}
