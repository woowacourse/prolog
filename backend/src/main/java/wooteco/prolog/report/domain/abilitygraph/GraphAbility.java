package wooteco.prolog.report.domain.abilitygraph;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import wooteco.prolog.report.domain.ablity.Ability;
import wooteco.prolog.report.domain.common.Updatable;

@Entity
@AllArgsConstructor
public class GraphAbility implements Updatable<GraphAbility> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ability_id", nullable = false)
    private Ability ability;

    @Column(nullable = false)
    private Long weight;

    private Boolean isPresent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ability_graph_id", nullable = false)
    private AbilityGraph abilityGraph;

    protected GraphAbility() {
    }

    public GraphAbility(Ability ability, Long weight, Boolean isPresent) {
        this(null, ability, weight, isPresent, null);
    }

    public GraphAbility(Ability ability, Long weight, Boolean isPresent, AbilityGraph abilityGraph) {
        this(null, ability, weight, isPresent, abilityGraph);
    }

    public void appendTo(AbilityGraph abilityGraph) {
        this.abilityGraph = abilityGraph;
    }

    public Long getId() {
        return id;
    }

    public Long getAbilityId() {
        return ability.getId();
    }

    public String getName() {
        return ability.getName();
    }

    public Long getWeight() {
        return weight;
    }

    public Boolean isParent() {
        return ability.isParent();
    }

    public Boolean isPresent() {
        return isPresent;
    }

    public Ability getAbility() {
        return ability;
    }

    @Override
    public void update(GraphAbility ability) {
        this.ability = ability.getAbility();
        this.weight = ability.getWeight();
    }

    @Override
    public boolean isSemanticallyEquals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GraphAbility)) {
            return false;
        }
        GraphAbility that = (GraphAbility) o;

        return Objects.equals(getAbility(), that.getAbility());
    }

    @Override
    public int semanticallyHashcode() {
        return this.ability.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GraphAbility)) {
            return false;
        }
        GraphAbility that = (GraphAbility) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
