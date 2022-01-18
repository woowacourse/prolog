package wooteco.prolog.report.domain.ablity;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class StudylogAbility {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private History history;

    @ManyToOne(fetch = FetchType.LAZY)
    private Ability2 ability;

    private Long studylogId;

    protected StudylogAbility() {
    }

    public StudylogAbility(Long id, History history, Ability2 ability, Long studylogId) {
        this.id = id;
        this.history = history;
        this.ability = ability;
        this.studylogId = studylogId;
    }

    public Long getId() {
        return id;
    }

    public Ability2 getAbility() {
        return ability;
    }

    public Long getStudylogId() {
        return studylogId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudylogAbility that = (StudylogAbility) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
