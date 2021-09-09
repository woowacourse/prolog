package wooteco.prolog.studylog.application.dto.report.studylogs;

public class AbilityResponse {
    private Long id;
    private String name;
    private String color;
    private Boolean isPresent;

    private AbilityResponse() {
    }

    public AbilityResponse(Long id, String name, String color, Boolean isPresent) {
        this.id = id;
        this.name = name;
        this.color = color;
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

    public Boolean getPresent() {
        return isPresent;
    }
}
