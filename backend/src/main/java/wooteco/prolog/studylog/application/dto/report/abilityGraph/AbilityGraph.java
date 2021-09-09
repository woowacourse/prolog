package wooteco.prolog.studylog.application.dto.report.abilityGraph;

import java.util.List;

public class AbilityGraph {

    private List<AbilityResponse> abilities;

    private AbilityGraph() {
    }

    public AbilityGraph(List<AbilityResponse> abilities) {
        this.abilities = abilities;
    }

    public List<AbilityResponse> getAbilities() {
        return abilities;
    }
}
