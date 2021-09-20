package wooteco.prolog.studylog.application.dto.report.response.studylogs;

import wooteco.prolog.studylog.domain.report.studylog.ReportedStudylogAbility;

public class StudylogAbilityResponse {
    private Long id;
    private String name;
    private String color;
    private Boolean isParent;

    private StudylogAbilityResponse() {
    }

    public StudylogAbilityResponse(Long id, String name, String color, Boolean isParent) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.isParent = isParent;
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

    public Boolean getParent() {
        return isParent;
    }
}
