package wooteco.prolog.studylog.domain.report.abilitygraph;

import static java.util.stream.Collectors.toList;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import wooteco.prolog.studylog.domain.report.Report;
import wooteco.prolog.studylog.domain.report.abilitygraph.datastructure.GraphAbility;
import wooteco.prolog.studylog.domain.report.common.UpdateUtil;

@Embeddable
public class ReportedAbilities {

    @OneToMany(
        fetch = FetchType.LAZY,
        cascade = CascadeType.PERSIST,
        orphanRemoval = true
    )
    private List<ReportedAbility> abilities;

    protected ReportedAbilities() {
    }

    public ReportedAbilities(List<ReportedAbility> abilities) {
        this.abilities = abilities;
    }

    public void appendTo(Report report) {
        this.abilities.forEach(ability -> ability.appendTo(report));
    }

    public void update(ReportedAbilities reportedAbilities, Report report) {
        reportedAbilities.abilities.forEach(ability -> ability.appendTo(report));

        UpdateUtil.execute(this.abilities, abilities);
    }

    public List<GraphAbility> graphAbilities() {
        Long allWeight = allWeight();

        return abilities.stream()
            .map(ability -> new GraphAbility(
                ability.getId(),
                ability.getName(),
                ability.getWeight(),
                calculatePercentage(allWeight, ability)
            )).collect(toList());
    }

    private Double calculatePercentage(Long allWeight, ReportedAbility ability) {
        double percentage = ability.getWeight() / (double) allWeight;
        return  Math.round(percentage * 100) / 100.0;
    }

    private Long allWeight() {
        return abilities.stream()
            .mapToLong(ReportedAbility::getWeight)
            .sum();
    }
}
