package wooteco.prolog.report.domain.report.studylog;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import wooteco.prolog.studylog.domain.Studylog;
import wooteco.prolog.report.domain.report.Report;
import wooteco.prolog.report.domain.report.common.Updatable;
import wooteco.prolog.report.domain.report.common.UpdateUtil;

@Entity
public class ReportedStudylog implements Updatable<ReportedStudylog> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long studylogId;

    @OneToMany(
        mappedBy = "reportedStudylog",
        fetch = FetchType.LAZY,
        cascade = CascadeType.PERSIST,
        orphanRemoval = true
    )
    private List<ReportedStudylogAbility> abilities;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "report_id", nullable = false)
    private Report report;

    protected ReportedStudylog() {
    }

    public ReportedStudylog(Long studylogId,
                            List<ReportedStudylogAbility> abilities
    ) {
        this(null, studylogId, abilities, null);
    }

    public ReportedStudylog(Long id,
                            Long studylogId,
                            List<ReportedStudylogAbility> abilities,
                            Report report) {
        this.id = id;
        this.studylogId = studylogId;
        this.abilities = abilities;
        this.report = report;

        this.abilities.forEach(ability -> ability.appendTo(this));
    }

    public Long getId() {
        return id;
    }

    public void appendTo(Report report) {
        this.report = report;
    }

    public List<ReportedStudylogAbility> getAbilities() {
        return abilities;
    }

    public Long getStudylogId() {
        return studylogId;
    }

    @Override
    public void update(ReportedStudylog reportedStudylog) {
        reportedStudylog.getAbilities().forEach(abilitiy -> abilitiy.appendTo(this));
        UpdateUtil.execute(getAbilities(), reportedStudylog.getAbilities());
    }

    @Override
    public boolean isSemanticallyEquals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ReportedStudylog)) {
            return false;
        }

        ReportedStudylog that = (ReportedStudylog) o;
        return Objects.equals(getStudylogId(), that.getStudylogId());
    }

    @Override
    public int semanticallyHashcode() {
        return Objects.hashCode(getStudylogId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ReportedStudylog)) {
            return false;
        }

        ReportedStudylog that = (ReportedStudylog) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
