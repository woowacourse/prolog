package wooteco.prolog.report.application.dto.Ability2;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@AllArgsConstructor
@ToString
public class StudylogAbilityMappingResponse {
    private Long id;
    private List<String> abilityNames;

    private StudylogAbilityMappingResponse() {
    }
}
