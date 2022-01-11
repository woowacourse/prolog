package wooteco.prolog.report.domain.ablity;

import javax.persistence.*;

@Entity
public class SnapshotAbility {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "abilitiesSnapshot_id")
    private History history;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "ability_id")
    private Ability2 ability2;

    protected SnapshotAbility() {
    }

    public SnapshotAbility(History history, Ability2 ability) {
        this(null, history, ability);
    }

    public SnapshotAbility(Long id, History history, Ability2 ability2) {
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
}
