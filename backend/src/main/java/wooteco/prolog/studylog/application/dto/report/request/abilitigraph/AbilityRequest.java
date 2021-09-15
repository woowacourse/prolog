package wooteco.prolog.studylog.application.dto.report.request.abilitigraph;

public class AbilityRequest {
    private Long id;
    private Long weight;
    private Boolean isPresent;

    private AbilityRequest() {
    }

    public AbilityRequest(Long id, Long weight, Boolean isPresent) {
        this.id = id;
        this.weight = weight;
        this.isPresent = isPresent;
    }

    public Long getId() {
        return id;
    }

    public Long getWeight() {
        return weight;
    }

    public Boolean isPresent() {
        return isPresent;
    }
}
