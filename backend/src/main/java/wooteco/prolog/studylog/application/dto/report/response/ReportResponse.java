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
    private GraphResponse graphResponse;
    private List<StudylogResponse> studylogs;
    private Boolean isRepresent;

    private ReportResponse() {
    }

    public ReportResponse(Long id,
                          String title,
                          String description,
                          GraphResponse graphResponse,
                          List<StudylogResponse> studylogs,
                          Boolean isRepresent) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.graphResponse = graphResponse;
        this.studylogs = studylogs;
        this.isRepresent = isRepresent;
    }

    public static ReportResponse from(Report report) {
        GraphResponse graphResponse = GraphResponse.from(report.getAbilityGraph());
        List<StudylogResponse> studylogs = report.getStudylogs().stream()
            .map(StudylogResponse::from)
            .collect(toList());

        return new ReportResponse(
            report.getId(),
            report.getTitle(),
            report.getDescription(),
            graphResponse,
            studylogs,
            report.isRepresent()
        );
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
        return graphResponse;
    }

    public List<StudylogResponse> getStudylogs() {
        return studylogs;
    }

    public Boolean getRepresent() {
        return isRepresent;
    }
}
