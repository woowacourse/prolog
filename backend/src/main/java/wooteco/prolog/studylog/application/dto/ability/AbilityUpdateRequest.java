package wooteco.prolog.studylog.application.dto.ability;

import wooteco.prolog.studylog.domain.ablity.Ability;

public class AbilityUpdateRequest {

    private Long id;
    private String name;
    private String description;
    private String color;

    public AbilityUpdateRequest() {
    }

    public AbilityUpdateRequest(Long id, String name, String description, String color) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.color = color;
    }


    public Ability toEntity() {
        return Ability.updateTarget(id, name, description, color);
    }
}
