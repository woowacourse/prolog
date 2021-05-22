package wooteco.prolog.post.application.dto;

import wooteco.prolog.post.domain.Post;

import java.util.List;

public class PostRequest {
    private String title;
    private String content;
    private String category;
    private List<String> tags;

    public PostRequest() {
    }

    public PostRequest(String title, String content, String category, List<String> tags) {
        this.title = title;
        this.content = content;
        this.category = category;
        this.tags = tags;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getCategory() {
        return category;
    }

    public List<String> getTags() {
        return tags;
    }

    public Post toEntity() {
        return new Post(null, title, tags, content);
    }
}