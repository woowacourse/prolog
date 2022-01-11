package wooteco.prolog.report.domain.ablity;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class HistoryAbility {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
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

    public Long getId() {
        return id;
    }

    public History getAbilitiesSnapshot() {
        return history;
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
