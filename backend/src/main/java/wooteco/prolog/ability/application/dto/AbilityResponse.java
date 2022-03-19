package wooteco.prolog.ability.application.dto;

import static java.util.stream.Collectors.toList;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import wooteco.prolog.ability.domain.Ability;

@AllArgsConstructor
@Getter
public class AbilityResponse {

    private Long id;
    private String name;
    private String description;
    private String color;
    private boolean isParent;
    private List<ChildAbilityResponse> children;

    public static List<AbilityResponse> listOf(List<Ability> abilities) {
        return abilities.stream()
            .map(AbilityResponse::of)
            .collect(toList());
    }

    public static AbilityResponse of(Ability ability) {
        return new AbilityResponse(
            ability.getId(),
            ability.getName(),
            ability.getDescription(),
            ability.getColor(),
            ability.isParent(),
            ChildAbilityResponse.of(ability.getChildren())
        );
    }

    public boolean getIsParent() {
        return isParent;
    }
}
