package wooteco.prolog.report.application.dto.request;

import java.util.List;
import wooteco.prolog.report.application.dto.request.abilitigraph.GraphRequest;
import wooteco.prolog.report.application.dto.request.studylog.ReportStudylogRequest;

public class ReportRequest {

    private Long id;
    private String title;
    private String description;
    private GraphRequest abilityGraph;
    private List<ReportStudylogRequest>studylogs;
    private Boolean represent;

    private ReportRequest() {
    }

    public ReportRequest(Long id,
                         String title,
                         String description,
                         GraphRequest abilityGraph,
                         List<ReportStudylogRequest> studylogs,
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

    public GraphRequest getAbilityGraph() {
        return abilityGraph;
    }

    public List<ReportStudylogRequest> getStudylogs() {
        return studylogs;
    }

    public Boolean isRepresent() {
        return represent;
    }
}
