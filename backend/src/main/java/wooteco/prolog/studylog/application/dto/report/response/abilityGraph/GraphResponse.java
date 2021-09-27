package wooteco.prolog.studylog.application.dto.report.response.abilityGraph;

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
        wooteco.prolog.studylog.domain.report.abilitygraph.AbilityGraph abilityGraph) {
        List<GraphAbilityResponse> graphAbilityRespons = abilityGraph.getAbilities().stream()
            .map(GraphAbilityResponse::from)
            .collect(toList());

        return new GraphResponse(graphAbilityRespons);
    }

    public List<GraphAbilityResponse> getAbilities() {
        return abilities;
    }
}
