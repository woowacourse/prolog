package wooteco.prolog.studylog.application.dto;

public class SimpleReportResponse {

    private Long id;
    private String name;
    private Boolean isRepresent;

    private SimpleReportResponse() {
    }

    public SimpleReportResponse(Long id, String name, Boolean isRepresent) {
        this.id = id;
        this.name = name;
        this.isRepresent = isRepresent;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Boolean getRepresent() {
        return isRepresent;
    }
}
