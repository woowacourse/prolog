package wooteco.prolog.studylog.application.dto.report.request;

import java.util.List;
import wooteco.prolog.studylog.application.dto.report.request.abilitigraph.GraphRequest;
import wooteco.prolog.studylog.application.dto.report.request.studylog.StudylogRequest;

public class ReportRequest {
    private String title;
    private String description;
    private GraphRequest graphRequest;
    private List<StudylogRequest>studylogs;
    private Boolean isRepresent;

    private ReportRequest() {
    }

    public ReportRequest(String title,
                         String description,
                         GraphRequest graphRequest,
                         List<StudylogRequest> studylogs,
                         Boolean isRepresent) {
        this.title = title;
        this.description = description;
        this.graphRequest = graphRequest;
        this.studylogs = studylogs;
        this.isRepresent = isRepresent;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public GraphRequest getAbilityGraph() {
        return graphRequest;
    }

    public List<StudylogRequest> getStudylogs() {
        return studylogs;
    }

    public Boolean isRepresent() {
        return isRepresent;
    }
}
