package wooteco.studylog.log.web.controller.dto;

import java.util.List;

public class LogRequest {
    private Long id;
    private String title;
    private List<String> tags;
    private String content;

    public LogRequest() {
    }

    public LogRequest(Long id, String title, List<String> tags, String content) {
        this.id = id;
        this.title = title;
        this.tags = tags;
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public List<String> getTags() {
        return tags;
    }

    public String getContent() {
        return content;
    }
}
