package wooteco.prolog.studylog.domain.report.studylog;

import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import org.springframework.data.annotation.LastModifiedDate;
import wooteco.prolog.studylog.domain.Studylog;
import wooteco.prolog.studylog.domain.report.Report;

@Entity
@AllArgsConstructor
public class ReportedStudylog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Studylog studylog;

    @OneToMany(mappedBy = "reportedStudylog", fetch = FetchType.LAZY)
    private List<ReportedStudylogAbility> abilities;

    @ManyToOne(fetch = FetchType.LAZY)
    private Report report;


    protected ReportedStudylog() {
    }

    public ReportedStudylog(Studylog studylog,
                            List<ReportedStudylogAbility> abilities,
                            Report report) {
        this.studylog = studylog;
        this.abilities = abilities;
        this.report = report;
    }

    public Long getId() {
        return id;
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
}
