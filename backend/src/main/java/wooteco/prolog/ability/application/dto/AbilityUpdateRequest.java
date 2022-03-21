package wooteco.prolog.ability.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wooteco.prolog.ability.domain.Ability;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AbilityUpdateRequest {

    private Long id;
    private String name;
    private String description;
    private String color;

    public Ability toEntity() {
        return new Ability(id, name, description, color);
    }
}
