package wooteco.prolog.studylog.domain.report.studylog;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import wooteco.prolog.studylog.domain.ablity.Ability;

@Entity
@AllArgsConstructor
public class ReportedStudylogAbility {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ability_id")
    private Ability ability;

    private Boolean isPresent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reportedStudylog_id")
    private ReportedStudylog reportedStudylog;

    protected ReportedStudylogAbility() {
    }

    public ReportedStudylogAbility(Ability ability,
                                   Boolean isPresent,
                                   ReportedStudylog reportedStudylog) {
        this(null, ability, isPresent, reportedStudylog);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return ability.getName();
    }

    public String getColor() {
        return ability.getColor();
    }

    public Boolean isPresent() {
        return isPresent;
    }
}
