package wooteco.prolog.studylog.application.dto.report.request.abilitigraph;

public class AbilityRequest {

    private Long id;
    private Long weight;
    private Boolean represent;

    private AbilityRequest() {
    }

    public AbilityRequest(Long id, Long weight, Boolean represent) {
        this.id = id;
        this.weight = weight;
        this.represent = represent;
    }

    public Long getId() {
        return id;
    }

    public Long getWeight() {
        return weight;
    }

    public Boolean isRepresent() {
        return represent;
    }
}
