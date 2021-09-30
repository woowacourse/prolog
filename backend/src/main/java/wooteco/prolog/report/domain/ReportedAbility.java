package wooteco.prolog.report.domain;

import javax.persistence.ManyToOne;
import wooteco.prolog.studylog.domain.Studylog;
import wooteco.prolog.report.domain.ablity.Ability;

public class ReportedAbility {

    @ManyToOne
    private Studylog studylog;

    @ManyToOne
    private Ability ability;
}
