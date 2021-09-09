package wooteco.prolog.studylog.application;

import java.util.Objects;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.studylog.application.dto.ability.AbilityRequest;
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
    public void createAbility(Member member, AbilityRequest abilityRequest) {
        Ability ability = extractAbility(member, abilityRequest);

        abilityRepository.save(ability);
    }

    private Ability extractAbility(Member member, AbilityRequest abilityRequest) {
        String name = abilityRequest.getName();
        String description = abilityRequest.getDescription();
        String color = abilityRequest.getColor();
        Long parentId = abilityRequest.getParentId();

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
}
