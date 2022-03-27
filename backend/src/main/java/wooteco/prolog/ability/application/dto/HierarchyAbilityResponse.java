package wooteco.prolog.ability.application.dto;

import static java.util.stream.Collectors.toList;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import wooteco.prolog.ability.domain.Ability;

@AllArgsConstructor
@Getter
public class HierarchyAbilityResponse {

    private Long id;
    private String name;
    private String description;
    private String color;
    private boolean isParent;
    private List<AbilityResponse> children;

    public static List<HierarchyAbilityResponse> listOf(List<Ability> abilities) {
        return abilities.stream()
            .map(HierarchyAbilityResponse::of)
            .collect(toList());
    }

    public static List<HierarchyAbilityResponse> flatListOf(List<Ability> abilities) {
        return abilities.stream()
            .map(HierarchyAbilityResponse::flatOf)
            .collect(toList());
    }

    public static HierarchyAbilityResponse of(Ability ability) {
        return new HierarchyAbilityResponse(
            ability.getId(),
            ability.getName(),
            ability.getDescription(),
            ability.getColor(),
            ability.isParent(),
            AbilityResponse.listOf(ability.getChildren())
        );
    }

    public static HierarchyAbilityResponse flatOf(Ability ability) {
        return new HierarchyAbilityResponse(
            ability.getId(),
            ability.getName(),
            ability.getDescription(),
            ability.getColor(),
            ability.isParent(),
            null
        );
    }

    public boolean getIsParent() {
        return isParent;
    }
}
