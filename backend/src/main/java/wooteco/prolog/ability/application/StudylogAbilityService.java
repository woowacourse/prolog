package wooteco.prolog.ability.application;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wooteco.prolog.ability.application.dto.AbilityResponse;
import wooteco.prolog.ability.application.dto.AbilityStudylogResponse;
import wooteco.prolog.ability.application.dto.StudylogAbilityRequest;
import wooteco.prolog.ability.domain.Ability;
import wooteco.prolog.ability.domain.StudylogAbility;
import wooteco.prolog.ability.domain.repository.StudylogAbilityRepository;
import wooteco.prolog.common.PageableResponse;
import wooteco.prolog.member.application.MemberService;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.studylog.application.StudylogService;
import wooteco.prolog.studylog.domain.Studylog;
import wooteco.prolog.studylog.event.StudylogDeleteEvent;

@Service
@Transactional(readOnly = true)
public class StudylogAbilityService {

    private StudylogAbilityRepository studylogAbilityRepository;
    private StudylogService studylogService;
    private AbilityService abilityService;
    private MemberService memberService;

    public StudylogAbilityService(StudylogAbilityRepository studylogAbilityRepository,
                                  StudylogService studylogService, AbilityService abilityService,
                                  MemberService memberService) {
        this.studylogAbilityRepository = studylogAbilityRepository;
        this.studylogService = studylogService;
        this.abilityService = abilityService;
        this.memberService = memberService;
    }

    @Transactional
    public List<AbilityResponse> updateStudylogAbilities(Long memberId, Long studylogId,
                                                         StudylogAbilityRequest studylogAbilityRequest) {
        Studylog studylog = studylogService.findStudylogById(studylogId);
        studylog.validateBelongTo(memberId);

        List<Ability> abilities = abilityService.findByIdIn(memberId,
            studylogAbilityRequest.getAbilities());
        // 자식 역량이 있는데 부모 역량이 있는 경우 예외처리
        abilities.stream()
            .filter(it -> !it.isParent())
            .filter(it -> abilities.contains(it.getParent()))
            .findFirst()
            .ifPresent(it -> {
                throw new IllegalArgumentException("자식 역량이 존재하는 경우 부모 역량을 선택할 수 없습니다.");
            });

        List<StudylogAbility> studylogAbilities = abilities.stream()
            .map(it -> new StudylogAbility(memberId, it, studylog))
            .collect(Collectors.toList());

        studylogAbilityRepository.deleteByStudylogId(studylogId);
        List<StudylogAbility> persistStudylogAbilities = studylogAbilityRepository.saveAll(
            studylogAbilities);

        return persistStudylogAbilities.stream()
            .map(it -> AbilityResponse.of(it.getAbility()))
            .collect(Collectors.toList());
    }

    public PageableResponse<AbilityStudylogResponse> findAbilityStudylogsByAbilityIds(
        String username, List<Long> abilityIds, Pageable pageable) {
        if (abilityIds != null && !abilityIds.isEmpty()) {

            Page<StudylogAbility> studylogAbilities = studylogAbilityRepository.findByAbilityIdIn(
                abilityIds, pageable);
            List<AbilityStudylogResponse> abilityStudylogResponses = AbilityStudylogResponse.listOf(
                studylogAbilities.getContent());
            return PageableResponse.of(abilityStudylogResponses, studylogAbilities);
        }

        Page<Studylog> studylogs = studylogService.findStudylogsByUsername(username, pageable);
        List<Long> studylogIds = studylogs.getContent().stream()
            .map(Studylog::getId)
            .collect(Collectors.toList());

        List<StudylogAbility> studylogAbilities = studylogAbilityRepository.findByStudylogIdIn(
            studylogIds);

        List<AbilityStudylogResponse> abilityStudylogResponses = AbilityStudylogResponse.listOf(
            studylogs.getContent(), studylogAbilities);
        return PageableResponse.of(abilityStudylogResponses, studylogs);
    }

    public PageableResponse<AbilityStudylogResponse> findAbilityStudylogsMappingOnlyByAbilityIds(
        String username, List<Long> abilityIds, Pageable pageable) {
        if (abilityIds != null && !abilityIds.isEmpty()) {
            Page<StudylogAbility> studylogAbilities = studylogAbilityRepository.findByAbilityIdIn(
                abilityIds, pageable);
            List<AbilityStudylogResponse> abilityStudylogResponses = AbilityStudylogResponse.listOf(
                studylogAbilities.getContent());
            return PageableResponse.of(abilityStudylogResponses, studylogAbilities);
        }

        Member member = memberService.findByUsername(username);

        Page<StudylogAbility> studylogAbilities = studylogAbilityRepository.findByMemberId(
            member.getId(), pageable);

        List<AbilityStudylogResponse> abilityStudylogResponses = AbilityStudylogResponse.listOf(
            studylogAbilities.getContent());
        return PageableResponse.of(abilityStudylogResponses, studylogAbilities);
    }

    public List<StudylogAbility> findStudylogAbilitiesInPeriod(Long memberId, LocalDate startDate,
                                                               LocalDate endDate) {
        List<Studylog> studylogs = studylogService.findStudylogsInPeriod(memberId, startDate,
            endDate);
        return studylogAbilityRepository.findByStudylogIdIn(
            studylogs.stream().map(Studylog::getId).collect(Collectors.toList()));
    }

    @EventListener
    public void onStudylogDeleteEvent(StudylogDeleteEvent event) {
        studylogAbilityRepository.deleteByStudylogId(event.getStudylogId());
    }
}
