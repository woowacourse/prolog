package wooteco.prolog.studylog.domain.report.abilitygraph;

import java.util.List;
import java.util.Objects;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import wooteco.prolog.studylog.domain.report.Report;
import wooteco.prolog.studylog.domain.report.abilitygraph.datastructure.GraphAbility;

@Entity
@AllArgsConstructor
public class Graph {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private ReportedAbilities reportedAbilities;

    protected Graph() {
    }

    public Graph(ReportedAbilities reportedAbilities) {
        this(null, reportedAbilities);
    }

    public void update(Graph graph, Report report) {
        reportedAbilities.update(graph.reportedAbilities, report);
    }

    public void appendTo(Report report) {
        reportedAbilities.appendTo(report);
    }

    public List<GraphAbility> getAbilities() {
        return reportedAbilities.graphAbilities();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Graph)) {
            return false;
        }
        Graph graph = (Graph) o;
        return Objects.equals(id, graph.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
