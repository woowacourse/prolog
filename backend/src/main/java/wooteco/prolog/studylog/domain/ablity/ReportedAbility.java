package wooteco.prolog.studylog.domain.ablity;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import wooteco.prolog.studylog.domain.Studylog;

public class ReportedAbility {

    @ManyToOne
    @JoinColumn(name = "report_id")
    private Report report;

    @ManyToOne
    private Studylog studylog;

    @ManyToOne
    private Ability ability;
}
