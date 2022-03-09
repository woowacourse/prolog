package wooteco.prolog.report.application.dto.response.studylogs;

import wooteco.prolog.report.domain.report.studylog.ReportedStudylogAbility;

public class StudylogAbilityResponse {
    private Long id;
    private String name;
    private String color;
    private Boolean parent;

    private StudylogAbilityResponse() {
    }

    public StudylogAbilityResponse(Long id, String name, String color, Boolean parent) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.parent = parent;
    }

    public static StudylogAbilityResponse from(ReportedStudylogAbility ability) {
        return new StudylogAbilityResponse(
            ability.getId(),
            ability.getName(),
            ability.getColor(),
            ability.isParent()
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

    public Boolean isParent() {
        return parent;
    }
}
