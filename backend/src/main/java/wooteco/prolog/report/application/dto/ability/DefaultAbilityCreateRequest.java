package wooteco.prolog.report.application.dto.ability;

import java.util.Objects;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wooteco.prolog.report.domain.ablity.DefaultAbility;

@Getter
@NoArgsConstructor
public class DefaultAbilityCreateRequest {

    private String name;
    private String description;
    private String color;
    private String template;
    private Long parentId;

    public DefaultAbilityCreateRequest(String name, String description, String color, String template) {
        this(name, description, color, template, null);
    }

    public DefaultAbilityCreateRequest(String name, String description, String color, String template,
                                       Long parentId) {
        this.name = name;
        this.description = description;
        this.color = color;
        this.template = template;
        this.parentId = parentId;
    }

    public boolean hasParent() {
        return !Objects.isNull(parentId);
    }
}
