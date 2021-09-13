package wooteco.prolog.studylog.application.dto.report.abilityGraph;

import static java.util.stream.Collectors.toList;

import java.util.List;
import wooteco.prolog.studylog.domain.report.abilitygraph.Graph;

public class AbilityGraph {

    private List<AbilityResponse> abilities;

    private AbilityGraph() {
    }

    public AbilityGraph(List<AbilityResponse> abilities) {
        this.abilities = abilities;
    }

    public static AbilityGraph from(Graph graph) {
        List<AbilityResponse> abilityResponses = graph.getAbilities().stream()
            .map(AbilityResponse::from)
            .collect(toList());

        return new AbilityGraph(abilityResponses);
    }

    public List<AbilityResponse> getAbilities() {
        return abilities;
    }
}
