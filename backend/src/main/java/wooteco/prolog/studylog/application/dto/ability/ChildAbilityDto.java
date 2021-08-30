package wooteco.prolog.studylog.application.dto.ability;

import lombok.AllArgsConstructor;
import lombok.Getter;
import wooteco.prolog.studylog.domain.ablity.Ability;

@AllArgsConstructor
@Getter
public class ChildAbilityDto {
    private Long id;
    private String name;
    private String description;
    private String color;
    private boolean isParent;

    public static ChildAbilityDto from(Ability ability) {
        return new ChildAbilityDto(
            ability.getId(),
            ability.getName(),
            ability.getDescription(),
            ability.getColor(),
            ability.isParent()
        );
    }
}
