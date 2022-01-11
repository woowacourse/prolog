package wooteco.prolog.report.domain.ablity;

import java.util.List;

import static wooteco.prolog.report.domain.ablity.domain.AbilityValidator.validateDuplicateAbilityColor;
import static wooteco.prolog.report.domain.ablity.domain.AbilityValidator.validateDuplicateAbilityName;

public class Abilities {

    private final List<Ability2> values;
    private final ObjectMapper objectMapper;

    public Abilities(List<Ability2> values, ObjectMapper objectMapper) {
        this.values = values;
        this.objectMapper = objectMapper;
    }

    public void add(Ability2 ability) {
        validateDuplicateAbilityName(ability, values);
        validateDuplicateAbilityColor(ability, values);
        this.values.add(ability);
    }

    public String toJson() {
        return objectMapper.toJson(values);
    }
}
