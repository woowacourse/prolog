package wooteco.prolog.report.application.dto.Ability2;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@AllArgsConstructor
@Getter
@ToString
public class AbilityUpdateRequest {

    private Long id;
    private String name;
    private String description;
    private String color;
    List<AbilityUpdateRequest> children;
}
