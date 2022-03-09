package wooteco.prolog.report.application.dto.request.abilitigraph;

import java.util.List;

public class GraphRequest {

    private List<AbilityRequest> abilities;

    private GraphRequest() {
    }

    public GraphRequest(List<AbilityRequest> abilities) {
        this.abilities = abilities;
    }

    public List<AbilityRequest> getAbilities() {
        return abilities;
    }
}
