package wooteco.prolog.report.application.dto.report.request.studylog;

import java.util.List;

public class ReportStudylogRequest {
    private Long id;
    private List<Long> abilities;

    private ReportStudylogRequest() {
    }

    public ReportStudylogRequest(Long id, List<Long> abilities) {
        this.id = id;
        this.abilities = abilities;
    }

    public Long getId() {
        return id;
    }

    public List<Long> getAbilities() {
        return abilities;
    }
}
