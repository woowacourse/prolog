package wooteco.prolog.studylog.domain.ablity;

import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Ability {

    private Long id;
    private String name;
    private String description;
    private String color;
    private Long parent;

    public boolean isParent() {
        return Objects.isNull(parent);
    }
}
