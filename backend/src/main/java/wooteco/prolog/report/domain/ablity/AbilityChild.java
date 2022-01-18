package wooteco.prolog.report.domain.ablity;

import javax.persistence.*;

@Entity
public class AbilityChild {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Ability2 source;

    @ManyToOne(fetch = FetchType.LAZY)
    private Ability2 target;

    protected AbilityChild() {
    }

    public AbilityChild(Ability2 source, Ability2 target) {
        this(null, source, target);
    }

    public AbilityChild(Long id, Ability2 source, Ability2 target) {
        this.id = id;
        this.source = source;
        this.target = target;
    }

    public Long getId() {
        return id;
    }

    public Ability2 getChild() {
        return target;
    }
}
