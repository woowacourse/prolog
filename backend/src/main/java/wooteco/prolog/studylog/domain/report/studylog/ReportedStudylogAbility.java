package wooteco.prolog.studylog.domain.report.studylog;

import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import wooteco.prolog.studylog.domain.ablity.Ability;
import wooteco.prolog.studylog.domain.report.common.Updatable;

@Entity
@AllArgsConstructor
public class ReportedStudylogAbility implements Updatable<ReportedStudylogAbility> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ability_id", nullable = false)
    private Ability ability;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reportedStudylog_id", nullable = false)
    private ReportedStudylog reportedStudylog;

    protected ReportedStudylogAbility() {
    }

    public ReportedStudylogAbility(Ability ability) {
        this(null, ability, null);
    }

    public ReportedStudylogAbility(Ability ability,
                                   ReportedStudylog reportedStudylog) {
        this(null, ability, reportedStudylog);
    }

    public void appendTo(ReportedStudylog reportedStudylog) {
        this.reportedStudylog = reportedStudylog;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return ability.getName();
    }

    public String getColor() {
        return ability.getColor();
    }

    public Boolean isParent() {
        return ability.isParent();
    }

    @Override
    public void update(ReportedStudylogAbility reportedStudylogAbility) {
        this.ability = reportedStudylogAbility.ability;
    }

    @Override
    public boolean isSemanticallyEquals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ReportedStudylogAbility)) {
            return false;
        }
        ReportedStudylogAbility that = (ReportedStudylogAbility) o;
        return Objects.equals(this.ability, that.ability);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ReportedStudylogAbility)) {
            return false;
        }
        ReportedStudylogAbility that = (ReportedStudylogAbility) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
