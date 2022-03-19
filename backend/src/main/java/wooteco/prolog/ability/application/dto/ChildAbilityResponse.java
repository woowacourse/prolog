package wooteco.prolog.ability.application.dto;

import static java.util.stream.Collectors.toList;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import wooteco.prolog.ability.domain.Ability;

@AllArgsConstructor
@Getter
public class ChildAbilityResponse {

    private Long id;
    private String name;
    private String description;
    private String color;
    private boolean isParent;

    public static List<ChildAbilityResponse> of(List<Ability> childAbilities) {
        return childAbilities.stream()
            .map(ChildAbilityResponse::from)
            .collect(toList());
    }

    public static ChildAbilityResponse from(Ability childAbility) {
        return new ChildAbilityResponse(
            childAbility.getId(),
            childAbility.getName(),
            childAbility.getDescription(),
            childAbility.getColor(),
            childAbility.isParent()
        );
    }

    public boolean getIsParent() {
        return isParent;
    }
}
