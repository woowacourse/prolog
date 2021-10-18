package wooteco.prolog.report.application.dto.report.response.ability_graph;

import wooteco.prolog.report.domain.report.abilitygraph.datastructure.GraphAbilityDto;

public class GraphAbilityResponse {

    private Long id;
    private String name;
    private String color;
    private Long weight;
    private Double percentage;
    private Boolean isPresent;

    private GraphAbilityResponse() {
    }

    public GraphAbilityResponse(Long id, String name, String color, Long weight, Double percentage, Boolean isPresent) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.weight = weight;
        this.percentage = percentage;
        this.isPresent = isPresent;
    }

    public static GraphAbilityResponse from(GraphAbilityDto graphAbilityDto) {
        return new GraphAbilityResponse(
            graphAbilityDto.getId(),
            graphAbilityDto.getName(),
            graphAbilityDto.getColor(),
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

    public String getColor() {
        return color;
    }

    public Long getWeight() {
        return weight;
    }

    public Double getPercentage() {
        return percentage;
    }

    public Boolean getIsPresent() {
        return isPresent;
    }
}
