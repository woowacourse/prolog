package wooteco.prolog.studylog.domain.report.studylog;

import java.util.List;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

@Embeddable
public class ReportedStudylogs {

    @OneToMany(mappedBy = "report", fetch = FetchType.LAZY)
    private List<ReportedStudylog> studylogs;

    public List<ReportedStudylog> getStudylogs() {
        return studylogs;
    }
}
