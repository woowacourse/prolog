package wooteco.prolog.studylog.domain.report.studylog;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
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
        this.values.forEach(studylog -> studylog.appendTo(report));
    }

    public void update(ReportedStudylogs studylogs, Report report) {
        studylogs.values.forEach(v -> v.appendTo(report));

        UpdateUtil.execute(this.values, studylogs.values);
    }

    public List<ReportedStudylog> getValues() {
        return values;
    }
}
