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
    public void createAbility(Member member, AbilityCreateRequest abilityCreateRequest) {
        Ability ability = extractAbility(member, abilityCreateRequest);

        abilityRepository.save(ability);
    }

    private Ability extractAbility(Member member, AbilityCreateRequest abilityCreateRequest) {
        String name = abilityCreateRequest.getName();
        String description = abilityCreateRequest.getDescription();
        String color = abilityCreateRequest.getColor();
        Long parentId = abilityCreateRequest.getParentId();

        if (Objects.isNull(parentId)) {
            return Ability.parent(name, description, color, member);
        }

        Ability parent = findAbilityById(parentId);
        return Ability.child(name, description, color, parent, member);
    }

    private Ability findAbilityById(Long parentId) {
        return abilityRepository.findById(parentId)
            .orElseThrow(AbilityNotFoundException::new);
    }

    @Transactional
    public List<AbilityResponse> updateAbility(Member member, AbilityUpdateRequest abilityUpdateRequest) {
        member.updateAbility(abilityUpdateRequest.toEntity());

        return AbilityResponse.of(member.getAbilities());
    }

    @Transactional
    public void deleteAbility(Member member, Long abilityId) {
        Ability ability = findAbilityById(abilityId);

        member.deleteAbility(ability);
    }
}
