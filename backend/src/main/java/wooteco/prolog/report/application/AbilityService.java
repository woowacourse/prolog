package wooteco.prolog.report.application;

import java.util.List;
import java.util.Objects;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wooteco.prolog.member.application.MemberService;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.report.application.dto.ability.AbilityCreateRequest;
import wooteco.prolog.report.application.dto.ability.AbilityResponse;
import wooteco.prolog.report.application.dto.ability.AbilityUpdateRequest;
import wooteco.prolog.report.domain.ablity.Ability;
import wooteco.prolog.report.domain.ablity.repository.AbilityRepository;
import wooteco.prolog.report.exception.AbilityNotFoundException;

@Service
@Transactional(readOnly = true)
public class AbilityService {

    private final AbilityRepository abilityRepository;
    private final MemberService memberService;

    public AbilityService(AbilityRepository abilityRepository, MemberService memberService) {
        this.abilityRepository = abilityRepository;
        this.memberService = memberService;
    }

    @Transactional
    public void createAbility(Long memberId, AbilityCreateRequest request) {
        Member member = memberService.findById(memberId);
        Ability ability = extractAbility(member, request);

        abilityRepository.save(ability);
    }

    private Ability extractAbility(Member member, AbilityCreateRequest request) {
        List<Ability> abilities = findByMemberId(member.getId());

        String name = request.getName();
        String description = request.getDescription();
        String color = request.getColor();
        Long parentId = request.getParent();

        if (Objects.isNull(parentId)) {
            return extractParentAbility(member, abilities, name, description, color);
        }

        return extractChildAbility(member, abilities, name, description, color, parentId);
    }

    private Ability extractChildAbility(Member member, List<Ability> abilities, String name, String description,
                                        String color, Long parentId) {
        Ability parentAbility = findAbilityById(parentId);
        Ability childAbility = Ability.child(name, description, color, parentAbility, member);

        childAbility.validateDuplicateName(abilities);
        childAbility.validateColorWithParent(abilities, parentAbility);

        return childAbility;
    }

    private Ability extractParentAbility(Member member, List<Ability> abilities, String name, String description,
                                         String color) {
        Ability parentAbility = Ability.parent(name, description, color, member);

        parentAbility.validateDuplicateName(abilities);
        parentAbility.validateDuplicateColor(abilities);

        return parentAbility;
    }

    public List<AbilityResponse> findAbilitiesByMemberId(Long memberId) {
        return AbilityResponse.of(findByMemberId(memberId));
    }

    public List<AbilityResponse> findParentAbilitiesByMemberId(Long memberId) {
        List<Ability> parentAbilities = abilityRepository.findByMemberIdAndParentIsNull(memberId);

        return AbilityResponse.of(parentAbilities);
    }

    @Transactional
    public List<AbilityResponse> updateAbility(Long memberId, AbilityUpdateRequest request) {
        Ability legacyAbility = findAbilityByIdAndMemberId(request.getId(), memberId);

        Ability updateAbility = request.toEntity();
        List<Ability> abilities = findByMemberId(memberId);
        abilities.remove(legacyAbility);
        updateAbility.validateDuplicateName(abilities);
        updateAbility.validateDuplicateColor(abilities);

        legacyAbility.update(updateAbility);

        return findAbilitiesByMemberId(memberId);
    }

    private List<Ability> findByMemberId(Long memberId) {
        return abilityRepository.findByMemberId(memberId);
    }

    @Transactional
    public void deleteAbility(Long memberId, Long abilityId) {
        Ability ability = findAbilityByIdAndMemberId(abilityId, memberId);
        ability.validateDeletable();

        ability.deleteRelationshipWithParent();
        abilityRepository.delete(ability);
    }

    private Ability findAbilityById(Long parentId) {
        return abilityRepository.findById(parentId)
            .orElseThrow(AbilityNotFoundException::new);
    }

    private Ability findAbilityByIdAndMemberId(Long abilityId, Long memberId) {
        return abilityRepository.findByIdAndMemberId(abilityId, memberId)
            .orElseThrow(AbilityNotFoundException::new);
    }
}
