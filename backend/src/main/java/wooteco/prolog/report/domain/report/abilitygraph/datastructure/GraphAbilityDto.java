package wooteco.prolog.report.domain.report.abilitygraph.datastructure;

public class GraphAbilityDto {

    private Long id;
    private String name;
    private String color;
    private Long weight;
    private Double percentage;
    private Boolean isParent;
    private Boolean isPresent;

    public GraphAbilityDto(Long id,
                           String name,
                           String color,
                           Long weight,
                           Double percentage,
                           Boolean isParent,
                           Boolean isPresent) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.weight = weight;
        this.percentage = percentage;
        this.isParent = isParent;
        this.isPresent = isPresent;
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

    public Boolean isParent() {
        return isParent;
    }

    public Boolean isPresent() {
        return isPresent;
    }
}
