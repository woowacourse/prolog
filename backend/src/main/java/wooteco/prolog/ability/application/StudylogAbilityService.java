package wooteco.prolog.ability.application;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wooteco.prolog.ability.application.dto.AbilityResponse;
import wooteco.prolog.ability.application.dto.AbilityStudylogResponse;
import wooteco.prolog.ability.application.dto.StudylogAbilityRequest;
import wooteco.prolog.ability.domain.Ability;
import wooteco.prolog.ability.domain.StudylogAbility;
import wooteco.prolog.ability.domain.repository.StudylogAbilityRepository;
import wooteco.prolog.member.application.MemberService;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.studylog.application.StudylogService;
import wooteco.prolog.studylog.domain.Studylog;

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

    public List<AbilityResponse> updateStudylogAbilities(Long memberId, Long studylogId, StudylogAbilityRequest studylogAbilityRequest) {
        Studylog studylog = studylogService.findStudylogById(studylogId);
        if (!studylog.isBelongsTo(memberId)) {
            throw new RuntimeException("자신의 학습로그의 역량만 수정이 가능합니다.");
        }

        List<Ability> abilities = abilityService.findByIdIn(memberId, studylogAbilityRequest.getAbilities());
        List<StudylogAbility> studylogAbilities = abilities.stream()
            .map(it -> new StudylogAbility(memberId, it, studylog))
            .collect(Collectors.toList());

        studylogAbilityRepository.deleteByStudylogId(studylogId);
        List<StudylogAbility> persistStudylogAbilities = studylogAbilityRepository.saveAll(studylogAbilities);

        return persistStudylogAbilities.stream()
            .map(it -> AbilityResponse.of(it.getAbility()))
            .collect(Collectors.toList());
    }

    public List<AbilityStudylogResponse> findAbilityStudylogsByAbilityIds(String username, List<Long> abilityIds) {
        if (abilityIds != null && !abilityIds.isEmpty()) {
            return AbilityStudylogResponse.listOf(studylogAbilityRepository.findByAbilityIn(abilityIds));
        }

        List<Studylog> studylogs = studylogService.findStudylogsByUsername(username, Pageable.unpaged());
        List<Long> studylogIds = studylogs.stream()
            .map(it -> it.getId())
            .collect(Collectors.toList());

        List<StudylogAbility> studylogAbilities = studylogAbilityRepository.findByStudylogIdIn(studylogIds);

        return AbilityStudylogResponse.listOf(studylogAbilities);
    }

    public List<AbilityStudylogResponse> findAbilityStudylogsMappingOnlyByAbilityIds(String username, List<Long> abilityIds) {
        if (abilityIds != null && !abilityIds.isEmpty()) {
            return AbilityStudylogResponse.listOf(studylogAbilityRepository.findByAbilityIn(abilityIds));
        }

        Member member = memberService.findByUsername(username);

        List<StudylogAbility> studylogAbilities = studylogAbilityRepository.findByMemberId(member.getId());

        return AbilityStudylogResponse.listOf(studylogAbilities);
    }

    public List<StudylogAbility> findAbilityStudylogs(String username, List<Long> abilityIds) {
        if (abilityIds != null && !abilityIds.isEmpty()) {
            return studylogAbilityRepository.findByAbilityIn(abilityIds);
        }

        Member member = memberService.findByUsername(username);

        return studylogAbilityRepository.findByMemberId(member.getId());
    }
}
