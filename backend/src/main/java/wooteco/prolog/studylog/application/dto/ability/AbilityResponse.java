package wooteco.prolog.studylog.application.dto.ability;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Map;
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

    public static List<AbilityResponse> from(List<Ability> abilities) {
        Map<Long, List<Ability>> abilitiesById = abilities.stream()
            .collect(groupingBy(Ability::getParent));

        return abilitiesById.keySet().stream()
            .map(abilitiesById::get)
            .map(AbilityResponse::createResponseForGroupedAbilities)
            .collect(toList());
    }

    private static AbilityResponse createResponseForGroupedAbilities(List<Ability> abilities) {
        List<ChildAbilityDto> childAbilityDtos = extractChildrenDtos(abilities);
        Ability parent = extractParent(abilities);

        return new AbilityResponse(
            parent.getId(),
            parent.getName(),
            parent.getDescription(),
            parent.getColor(),
            parent.isParent(),
            childAbilityDtos
        );
    }

    private static Ability extractParent(List<Ability> abilities) {
        return abilities.stream()
            .filter(Ability::isParent)
            .findAny()
            .orElseThrow(() -> new IllegalArgumentException("부모 역량은 존재해야 합니다."));
    }

    private static List<ChildAbilityDto> extractChildrenDtos(List<Ability> abilities) {
        return abilities.stream()
            .filter(ability -> !ability.isParent())
            .map(ChildAbilityDto::from)
            .collect(toList());
    }
}
