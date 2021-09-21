package wooteco.prolog.studylog.application.dto.report.response;

import static java.util.stream.Collectors.toList;

import java.util.List;
import wooteco.prolog.studylog.application.dto.report.response.abilityGraph.GraphResponse;
import wooteco.prolog.studylog.application.dto.report.response.studylogs.StudylogResponse;
import wooteco.prolog.studylog.domain.report.Report;

public class ReportResponse {

    private Long id;
    private String title;
    private String description;
    private GraphResponse abilityGraph;
    private List<StudylogResponse> studylogs;
    private Boolean represent;

    private ReportResponse() {
    }

    public ReportResponse(Long id,
                          String title,
                          String description,
                          GraphResponse abilityGraph,
                          List<StudylogResponse> studylogs,
                          Boolean represent) {
        this.id = id;
        this.title = title;
        this.description = description;
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
