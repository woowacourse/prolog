package wooteco.prolog.report.application.dto.response.ability_graph;

import static java.util.stream.Collectors.toList;

import java.util.List;

public class GraphResponse {

    private List<GraphAbilityResponse> abilities;

    private GraphResponse() {
    }

    public GraphResponse(List<GraphAbilityResponse> abilities) {
        this.abilities = abilities;
    }

    public static GraphResponse from(
        wooteco.prolog.report.domain.report.abilitygraph.AbilityGraph abilityGraph) {
        List<GraphAbilityResponse> graphAbilityRespons = abilityGraph.getAbilities().stream()
            .map(GraphAbilityResponse::from)
            .collect(toList());

        return new GraphResponse(graphAbilityRespons);
    }

    public List<GraphAbilityResponse> getAbilities() {
        return abilities;
    }
}
