package wooteco.studylog.post.web.controller.dto;

import java.util.List;

public class PostRequest {
    private Long id;
    private String title;
    private List<String> tags;
    private String content;

    public PostRequest() {
    }

    public PostRequest(Long id, String title, List<String> tags, String content) {
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