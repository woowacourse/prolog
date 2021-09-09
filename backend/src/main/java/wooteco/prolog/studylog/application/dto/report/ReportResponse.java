package wooteco.prolog.studylog.application.dto.report;

import java.util.List;
import wooteco.prolog.studylog.application.dto.report.abilityGraph.AbilityGraph;
import wooteco.prolog.studylog.application.dto.report.studylogs.StudylogResponse;

public class ReportResponse {
    private Long id;
    private String title;
    private String description;
    private AbilityGraph abilityGraph;
    private List<StudylogResponse> studylogs;
    private Boolean isRepresent;

    private ReportResponse() {
    }

    public ReportResponse(Long id,
                          String title,
                          String description,
                          AbilityGraph abilityGraph,
                          List<StudylogResponse> studylogs,
                          Boolean isRepresent) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.abilityGraph = abilityGraph;
        this.studylogs = studylogs;
        this.isRepresent = isRepresent;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public AbilityGraph getAbilityGraph() {
        return abilityGraph;
    }

    public List<StudylogResponse> getStudylogs() {
        return studylogs;
    }

    public Boolean getRepresent() {
        return isRepresent;
    }
}
