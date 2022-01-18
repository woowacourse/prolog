package wooteco.prolog.report.domain.ablity;

import wooteco.prolog.report.domain.ablity.vo.Name;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
public class HistoryAbility {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "abilitiesSnapshot_id")
    private History history;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "ability_id")
    private Ability2 ability2;

    protected HistoryAbility() {
    }

    public HistoryAbility(History history, Ability2 ability) {
        this(null, history, ability);
    }

    public HistoryAbility(Long id, History history, Ability2 ability2) {
        this.id = id;
        this.history = history;
        this.ability2 = ability2;
    }

    public boolean isSameAbilityName(String abilityName) {
        return ability2.isSameName(new Name(abilityName));
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return ability2.getName();
    }

    public String getDescription() {
        return ability2.getDescription();
    }

    public String getColor() {
        return ability2.getColor();
    }

    public Boolean isParent() {
        return ability2.isParent();
    }

    public List<Ability2> getChildren() {
        return ability2.getChildren();
    }

    public Ability2 getAbility() {
        return ability2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HistoryAbility that = (HistoryAbility) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
