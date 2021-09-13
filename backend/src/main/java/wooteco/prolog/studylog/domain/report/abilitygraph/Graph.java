package wooteco.prolog.studylog.domain.report.abilitygraph;

import java.util.List;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import wooteco.prolog.studylog.domain.report.abilitygraph.datastructure.GraphAbility;

@Embeddable
public class Graph {

    @Embedded
    private ReportedAbilities reportedAbilities;

    protected Graph() {
    }

    public Graph(List<ReportedAbility> reportedAbilities) {
        this.reportedAbilities = new ReportedAbilities(reportedAbilities);
    }

    public List<GraphAbility> getAbilities() {
        return reportedAbilities.graphAbilities();
    }
}
