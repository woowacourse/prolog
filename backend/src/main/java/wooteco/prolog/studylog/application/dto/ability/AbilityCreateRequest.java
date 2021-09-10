package wooteco.prolog.studylog.application.dto.ability;

public class AbilityCreateRequest {

    private String name;
    private String description;
    private String color;
    private Long parent;

    public AbilityCreateRequest() {
    }

    public AbilityCreateRequest(String name, String description, String color, Long parent) {
        this.name = name;
        this.description = description;
        this.color = color;
        this.parent = parent;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getColor() {
        return color;
    }

    public Long getParentId() {
        return parent;
    }
}
