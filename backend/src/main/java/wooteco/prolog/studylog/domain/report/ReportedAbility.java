package wooteco.prolog.studylog.domain.report;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import wooteco.prolog.studylog.domain.Studylog;
import wooteco.prolog.studylog.domain.ablity.Ability;

public class ReportedAbility {

    @ManyToOne
    private Studylog studylog;

    @ManyToOne
    private Ability ability;
}
