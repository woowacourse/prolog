package wooteco.prolog.report.application.dto.report.request.studylog;

import java.util.List;

public class StudylogRequest {
    private Long id;
    private List<Long> abilities;

    private StudylogRequest() {
    }

    public StudylogRequest(Long id, List<Long> abilities) {
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
