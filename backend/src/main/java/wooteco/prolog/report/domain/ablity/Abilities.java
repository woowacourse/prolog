package wooteco.prolog.report.domain.ablity;

import java.util.List;

public class Abilities {

    private final List<Ability2> values;
    private final ObjectMapper objectMapper;

    public Abilities(List<Ability2> values, ObjectMapper objectMapper) {
        this.values = values;
        this.objectMapper = objectMapper;
    }

    public void add(Ability2 ability) {
        this.values.add(ability);
    }

    public String toJson() {
        return objectMapper.toJson(values);
    }
}
