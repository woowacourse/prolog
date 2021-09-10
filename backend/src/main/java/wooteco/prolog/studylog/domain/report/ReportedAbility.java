package wooteco.prolog.studylog.domain.report;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import wooteco.prolog.studylog.domain.Studylog;
import wooteco.prolog.studylog.domain.ablity.Ability;

@Entity
public class ReportedAbility {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Studylog studylog;

    @ManyToOne(fetch = FetchType.LAZY)
    private Ability ability;

    @ManyToOne
    private Report report;

    protected ReportedAbility() {

    }

    public ReportedAbility(Studylog studylog, Ability ability) {
        this.studylog = studylog;
        this.ability = ability;
    }

    public Studylog getStudylog() {
        return studylog;
    }

    public Ability getAbility() {
        return ability;
    }
}
