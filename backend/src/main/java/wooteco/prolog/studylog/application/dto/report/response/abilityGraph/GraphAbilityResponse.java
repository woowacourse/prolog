package wooteco.prolog.studylog.application.dto.report.response.abilityGraph;

import wooteco.prolog.studylog.domain.report.abilitygraph.datastructure.GraphAbility;

public class GraphAbilityResponse {

    private Long id;
    private String name;
    private Long weight;
    private Double percentage;
    private Boolean present;

    private GraphAbilityResponse() {
    }

    public GraphAbilityResponse(Long id, String name, Long weight, Double percentage, Boolean present) {
        this.id = id;
        this.name = name;
        this.weight = weight;
        this.percentage = percentage;
        this.present = present;
    }

    public static GraphAbilityResponse from(GraphAbility graphAbility) {
        return new GraphAbilityResponse(
            graphAbility.getId(),
            graphAbility.getName(),
            graphAbility.getWeight(),
            graphAbility.getPercentage(),
            graphAbility.isPresent()
        );
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getWeight() {
        return weight;
    }

    public Double getPercentage() {
        return percentage;
    }

    public Boolean isPresent() {
        return present;
    }
}
