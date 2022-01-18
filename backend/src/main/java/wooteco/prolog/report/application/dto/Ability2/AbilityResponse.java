package wooteco.prolog.report.application.dto.Ability2;

import lombok.AllArgsConstructor;
import lombok.Getter;
import wooteco.prolog.report.application.dto.ability.ChildAbilityDto;

import java.util.List;

@AllArgsConstructor
@Getter
public class AbilityResponse {

    private Long id;
    private String name;
    private String description;
    private String color;
    private boolean isParent;
    List<AbilityResponse> children;
}
