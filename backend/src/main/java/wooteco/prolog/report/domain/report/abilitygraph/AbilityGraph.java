package wooteco.prolog.report.domain.report.abilitygraph;

import java.util.List;
import java.util.Objects;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import wooteco.prolog.report.domain.report.Report2;
import wooteco.prolog.report.domain.report.abilitygraph.datastructure.GraphAbilityDto;

@Entity
public class AbilityGraph {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private GraphAbilities graphAbilities;

    @ManyToOne
    @JoinColumn(name = "report_id")
    private Report2 report;

    protected AbilityGraph() {
    }

    public AbilityGraph(GraphAbilities graphAbilities) {
        this(null, graphAbilities, null);
    }

    public AbilityGraph(GraphAbilities graphAbilities, Report2 report) {
        this(null, graphAbilities, report);
    }

    public AbilityGraph(Long id, GraphAbilities graphAbilities, Report2 report) {
        this.id = id;
        this.graphAbilities = graphAbilities;
        this.report = report;

        graphAbilities.appendTo(this);
    }

    public GraphAbilities getGraphAbilities() {
        return graphAbilities;
    }

    public void update(AbilityGraph abilityGraph) {
        graphAbilities.update(abilityGraph.getGraphAbilities(), this);
    }

    public void appendTo(Report2 report) {
        this.report = report;
    }

    public List<GraphAbilityDto> getAbilities() {
        return graphAbilities.graphAbilities();
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
