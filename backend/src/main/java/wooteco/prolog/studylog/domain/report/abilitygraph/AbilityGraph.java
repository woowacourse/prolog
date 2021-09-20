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
public class AbilityGraph {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private ReportedAbilities reportedAbilities;

    protected AbilityGraph() {
    }

    public AbilityGraph(ReportedAbilities reportedAbilities) {
        this(null, reportedAbilities);
    }

    public void update(AbilityGraph abilityGraph, Report report) {
        reportedAbilities.update(abilityGraph.reportedAbilities, report);
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
        if (!(o instanceof AbilityGraph)) {
            return false;
        }
        AbilityGraph abilityGraph = (AbilityGraph) o;
        return Objects.equals(id, abilityGraph.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
