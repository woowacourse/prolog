package wooteco.prolog.studylog.domain.report.abilitygraph;

import static java.util.stream.Collectors.toList;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import wooteco.prolog.studylog.domain.report.Report;
import wooteco.prolog.studylog.domain.report.abilitygraph.datastructure.GraphAbilityDto;
import wooteco.prolog.studylog.domain.report.common.UpdateUtil;

@Embeddable
public class GraphAbilities {

    @OneToMany(
        mappedBy = "abilityGraph",
        fetch = FetchType.LAZY,
        cascade = CascadeType.PERSIST,
        orphanRemoval = true
    )
    private List<GraphAbility> abilities;

    protected GraphAbilities() {
    }

    public GraphAbilities(List<GraphAbility> abilities) {
        this.abilities = abilities;
    }

    public void appendTo(AbilityGraph abilityGraph) {
        this.abilities.forEach(ability -> ability.appendTo(abilityGraph));
    }

    public List<GraphAbility> getAbilities() {
        return abilities;
    }

    public void update(GraphAbilities graphAbilities, AbilityGraph abilityGraph) {
        graphAbilities.getAbilities().forEach(ability -> ability.appendTo(abilityGraph));

        UpdateUtil.execute(getAbilities(), graphAbilities.getAbilities());
    }

    public List<GraphAbilityDto> graphAbilities() {
        Long allWeight = allWeight();

        return abilities.stream()
            .map(ability -> new GraphAbilityDto(
                ability.getAbilityId(),
                ability.getName(),
                ability.getWeight(),
                calculatePercentage(allWeight, ability),
                ability.isPresent()
            )).collect(toList());
    }

    private Double calculatePercentage(Long allWeight, GraphAbility ability) {
        double percentage = ability.getWeight() / (double) allWeight;
        return  Math.round(percentage * 100) / 100.0;
    }

    private Long allWeight() {
        return abilities.stream()
            .mapToLong(GraphAbility::getWeight)
            .sum();
    }
}
