package wooteco.prolog.report.application.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import wooteco.prolog.report.application.dto.response.ability_graph.GraphResponse;
import wooteco.prolog.report.application.dto.response.studylogs.StudylogResponse;

public class ReportResponse {

    private Long id;
    private String title;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private GraphResponse abilityGraph;
    private List<StudylogResponse> studylogs;
    private Boolean represent;

    private ReportResponse() {
    }

    public ReportResponse(Long id,
                          String title,
                          String description,
                          LocalDateTime createdAt,
                          LocalDateTime updatedAt,
                          GraphResponse abilityGraph,
                          List<StudylogResponse> studylogs,
                          Boolean represent) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.abilityGraph = abilityGraph;
        this.studylogs = studylogs;
        this.represent = represent;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public GraphResponse getAbilityGraph() {
        return abilityGraph;
    }

    public List<StudylogResponse> getStudylogs() {
        return studylogs;
    }

    public Boolean isRepresent() {
        return represent;
    }
}
