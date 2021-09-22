package wooteco.prolog.studylog.application;

import java.util.List;
import java.util.Objects;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.studylog.application.dto.ability.AbilityCreateRequest;
import wooteco.prolog.studylog.application.dto.ability.AbilityResponse;
import wooteco.prolog.studylog.application.dto.ability.AbilityUpdateRequest;
import wooteco.prolog.studylog.domain.ablity.Ability;
import wooteco.prolog.studylog.domain.repository.AbilityRepository;
import wooteco.prolog.studylog.exception.AbilityNotFoundException;

@Service
@Transactional(readOnly = true)
public class AbilityService {

    private final AbilityRepository abilityRepository;

    public AbilityService(AbilityRepository abilityRepository) {
        this.abilityRepository = abilityRepository;
    }

    @Transactional
    public void createAbility(Member member, AbilityCreateRequest request) {
        Ability ability = extractAbility(member, request);

        abilityRepository.save(ability);
    }

    private Ability extractAbility(Member member, AbilityCreateRequest request) {
        String name = request.getName();
        String description = request.getDescription();
        String color = request.getColor();
        Long parentId = request.getParent();

        if (Objects.isNull(parentId)) {
            return Ability.parent(name, description, color, member);
        }

        Ability parent = findAbilityById(parentId);
        return Ability.child(name, description, color, parent, member);
    }

    public List<AbilityResponse> abilities(Member member) {
        List<Ability> abilities = abilityRepository.findByMember(member);

        return AbilityResponse.of(abilities);
    }

    public List<AbilityResponse> parentAbilities(Member member) {
        List<Ability> parentAbilities = abilityRepository.findByMemberAndParentIsNull(member);

        return AbilityResponse.of(parentAbilities);
    }

    @Transactional
    public List<AbilityResponse> updateAbility(Member member, AbilityUpdateRequest request) {
        Ability legacyAbility = findAbilityByIdAndMember(request.getId(), member);
        Ability updateAbility = request.toEntity();

        legacyAbility.update(updateAbility);

        return AbilityResponse.of(member.getAbilities());
    }

    @Transactional
    public void deleteAbility(Member member, Long abilityId) {
        Ability ability = findAbilityByIdAndMember(abilityId, member);
        ability.validateDeletable();

        member.deleteAbility(ability);
        abilityRepository.delete(ability);
    }

    private Ability findAbilityById(Long parentId) {
        return abilityRepository.findById(parentId)
            .orElseThrow(AbilityNotFoundException::new);
    }

    private Ability findAbilityByIdAndMember(Long abilityId, Member member) {
        return abilityRepository.findByIdAndMember(abilityId, member)
            .orElseThrow(AbilityNotFoundException::new);
    }
}
