package wooteco.prolog.studylog.domain.report.abilitygraph;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import wooteco.prolog.studylog.domain.ablity.Ability;
import wooteco.prolog.studylog.domain.report.Report;

@Entity
public class ReportedAbility {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ability_id")
    private Ability ability;

    private Long weight;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "report_id")
    private Report report;

    protected ReportedAbility() {
    }

    public ReportedAbility(Ability ability, Long weight, Report report) {
        this(null, ability, weight, report);
    }

    public ReportedAbility(Long id, Ability ability, Long weight, Report report) {
        this.id = id;
        this.ability = ability;
        this.weight = weight;
        this.report = report;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return ability.getName();
    }

    public Long getWeight() {
        return weight;
    }
}
