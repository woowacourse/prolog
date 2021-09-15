package wooteco.prolog.studylog.domain.report.studylog;

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
import wooteco.prolog.studylog.domain.report.Report;
import wooteco.prolog.studylog.domain.report.common.Updatable;
import wooteco.prolog.studylog.domain.report.common.UpdateUtil;

@Entity
public class ReportedStudylog implements Updatable<ReportedStudylog> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="studylog_id", nullable = false)
    private Studylog studylog;

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

    public ReportedStudylog(Studylog studylog,
                            List<ReportedStudylogAbility> abilities
    ) {
        this(null, studylog, abilities, null);
    }

    public ReportedStudylog(Long id,
                            Studylog studylog,
                            List<ReportedStudylogAbility> abilities,
                            Report report) {
        this.id = id;
        this.studylog = studylog;
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

    public LocalDateTime getCreatedAt() {
        return studylog.getCreatedAt();
    }

    public LocalDateTime getUpdatedAt() {
        return studylog.getUpdatedAt();
    }

    public String getTitle() {
        return studylog.getTitle();
    }

    public List<ReportedStudylogAbility> getAbilities() {
        return abilities;
    }

    @Override
    public void update(ReportedStudylog reportedStudylog) {
        reportedStudylog.abilities.forEach(abilitiy -> abilitiy.appendTo(this));
        UpdateUtil.execute(this.abilities, reportedStudylog.abilities);
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
