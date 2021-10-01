package wooteco.prolog.report.application.dto.report.response.abilityGraph;

import wooteco.prolog.report.domain.report.abilitygraph.datastructure.GraphAbilityDto;

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

    public static GraphAbilityResponse from(GraphAbilityDto graphAbilityDto) {
        return new GraphAbilityResponse(
            graphAbilityDto.getId(),
            graphAbilityDto.getName(),
            graphAbilityDto.getWeight(),
            graphAbilityDto.getPercentage(),
            graphAbilityDto.isPresent()
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
