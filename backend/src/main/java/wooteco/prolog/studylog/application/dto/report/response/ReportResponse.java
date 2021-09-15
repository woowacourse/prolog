package wooteco.prolog.studylog.application.dto.report.response;

import static java.util.stream.Collectors.toList;

import java.util.List;
import wooteco.prolog.studylog.application.dto.report.response.abilityGraph.AbilityGraph;
import wooteco.prolog.studylog.application.dto.report.response.studylogs.StudylogResponse;
import wooteco.prolog.studylog.domain.report.Report;

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

    public static ReportResponse from(Report report) {
        AbilityGraph abilityGraph = AbilityGraph.from(report.getGraph());
        List<StudylogResponse> studylogs = report.getStudylogs().stream()
            .map(StudylogResponse::from)
            .collect(toList());

        return new ReportResponse(
            report.getId(),
            report.getTitle(),
            report.getDescription(),
            abilityGraph,
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
