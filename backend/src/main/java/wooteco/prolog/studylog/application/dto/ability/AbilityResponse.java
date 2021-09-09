package wooteco.prolog.studylog.application.dto.ability;

import static java.util.stream.Collectors.toList;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import wooteco.prolog.studylog.domain.ablity.Ability;

@AllArgsConstructor
@Getter
public class AbilityResponse {
    private Long id;
    private String name;
    private String description;
    private String color;
    private boolean isParent;
    List<ChildAbilityDto> children;

    public static List<AbilityResponse> of(List<Ability> abilities) {
        return abilities.stream()
            .map(AbilityResponse::toAbilityResponse)
            .collect(toList());
    }

    private static AbilityResponse toAbilityResponse(Ability ability) {
        return new AbilityResponse(
            ability.getId(),
            ability.getName(),
            ability.getDescription(),
            ability.getColor(),
            ability.isParent(),
            ChildAbilityDto.of(ability.getChildren())
        );
    }
}
