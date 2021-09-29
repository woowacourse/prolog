package wooteco.prolog.studylog.domain.report.abilitygraph.datastructure;

public class GraphAbilityDto {

    private Long id;
    private String name;
    private Long weight;
    private Double percentage;
    private Boolean isPresent;

    public GraphAbilityDto(Long id, String name, Long weight, Double percentage, Boolean isPresent) {
        this.id = id;
        this.name = name;
        this.weight = weight;
        this.percentage = percentage;
        this.isPresent = isPresent;
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
        return isPresent;
    }
}
