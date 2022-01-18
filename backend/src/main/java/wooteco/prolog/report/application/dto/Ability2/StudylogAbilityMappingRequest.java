package wooteco.prolog.report.application.dto.Ability2;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.List;

@Getter
@RequiredArgsConstructor
@ToString
public class StudylogAbilityMappingRequest {
    private final Long id;
    private final List<String> abilityNames;
}
