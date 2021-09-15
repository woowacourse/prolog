package wooteco.prolog.studylog.application.dto.report.request.abilitigraph;

import java.util.List;

public class AbilityGraph {

    private List<AbilityRequest> abilities;

    private AbilityGraph() {
    }

    public List<AbilityRequest> getAbilities() {
        return abilities;
    }
}
