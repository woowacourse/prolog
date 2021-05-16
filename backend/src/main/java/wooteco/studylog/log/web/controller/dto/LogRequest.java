package wooteco.studylog.log.web.controller.dto;

import java.util.List;

public class LogRequest {
    Long categoryId;
    String title;
    List<String> tags;
    String content;

    public LogRequest() {
    }

    public LogRequest(Long categoryId, String title, List<String> tags, String content) {
        this.categoryId = categoryId;
        this.title = title;
        this.tags = tags;
        this.content = content;
    }

    public Long getCategoryId() {
        return categoryId;
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
