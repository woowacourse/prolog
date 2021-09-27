package wooteco.prolog.studylog.domain.ablity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class AbilityRelationship {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "source_id")
    private Ability source;

    @ManyToOne
    @JoinColumn(name = "target_id")
    private Ability target;

    public AbilityRelationship(Ability source, Ability target) {
        this.source = source;
        this.target = target;
    }

    public Long getId() {
        return id;
    }

    public Ability getSource() {
        return source;
    }

    public Ability getTarget() {
        return target;
    }
}
