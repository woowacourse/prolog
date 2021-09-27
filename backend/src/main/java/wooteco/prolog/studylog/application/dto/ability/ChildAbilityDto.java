package wooteco.prolog.studylog.application.dto.ability;

import static java.util.stream.Collectors.toList;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import wooteco.prolog.studylog.domain.ablity.Ability;

@AllArgsConstructor
public class ChildAbilityDto {
    private Long id;
    private String name;
    private String description;
    private String color;
    private boolean isParent;

    public static List<ChildAbilityDto> of(List<Ability> childAbilities) {
        return childAbilities.stream()
            .map(ChildAbilityDto::from)
            .collect(toList());
    }

    public static ChildAbilityDto from(Ability childAbility) {
        return new ChildAbilityDto(
            childAbility.getId(),
            childAbility.getName(),
            childAbility.getDescription(),
            childAbility.getColor(),
            childAbility.isParent()
        );
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getColor() {
        return color;
    }

    public boolean getIsParent() {
        return isParent;
    }
}
