package wooteco.prolog.ability.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AbilityCreateRequest {

    private String name;
    private String description;
    private String color;
    private Long parent;
}
