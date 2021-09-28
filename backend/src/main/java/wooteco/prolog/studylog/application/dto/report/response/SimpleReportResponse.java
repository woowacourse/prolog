package wooteco.prolog.studylog.application.dto.report.response;

public class SimpleReportResponse {

    private Long id;
    private String title;
    private Boolean isRepresent;

    private SimpleReportResponse() {
    }

    public SimpleReportResponse(Long id, String title, Boolean isRepresent) {
        this.id = id;
        this.title = title;
        this.isRepresent = isRepresent;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Boolean getRepresent() {
        return isRepresent;
    }
}
