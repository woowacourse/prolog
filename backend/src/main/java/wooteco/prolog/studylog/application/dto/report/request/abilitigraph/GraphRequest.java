package wooteco.prolog.studylog.application.dto.report.request.abilitigraph;

import java.util.List;

public class GraphRequest {

    private Long id;
    private List<AbilityRequest> abilities;

    private GraphRequest() {
    }

    public GraphRequest(Long id,
                        List<AbilityRequest> abilities) {
        this.id = id;
        this.abilities = abilities;
    }

    public Long getId() {
        return id;
    }

    public List<AbilityRequest> getAbilities() {
        return abilities;
    }
}
