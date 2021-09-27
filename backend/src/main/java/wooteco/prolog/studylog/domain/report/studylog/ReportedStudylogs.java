package wooteco.prolog.studylog.domain.report.studylog;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import org.hibernate.annotations.BatchSize;
import wooteco.prolog.studylog.domain.report.Report;
import wooteco.prolog.studylog.domain.report.common.UpdateUtil;

@Embeddable
public class ReportedStudylogs {

    @OneToMany(
        mappedBy = "report",
        fetch = FetchType.LAZY,
        cascade = CascadeType.PERSIST,
        orphanRemoval = true
    )
    private List<ReportedStudylog> values;

    protected ReportedStudylogs() {
    }

    public ReportedStudylogs(List<ReportedStudylog> values) {
        this.values = values;
    }

    public void appendTo(Report report) {
        getValues().forEach(studylog -> studylog.appendTo(report));
    }

    public void update(ReportedStudylogs studylogs, Report report) {
        studylogs.getValues().forEach(v -> v.appendTo(report));

        UpdateUtil.execute(getValues(), studylogs.getValues());
    }

    public List<ReportedStudylog> getValues() {
        return values;
    }
}
