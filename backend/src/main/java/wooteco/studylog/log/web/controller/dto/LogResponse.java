package wooteco.studylog.log.web.controller.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class LogResponse {
    private Long logId;
    private AuthorResponse author;
    private LocalDateTime issuedDate;
    private CategoryResponse category;
    private String title;
    private String content;
    private List<String> tags;


    public LogResponse() {
    }

    public LogResponse(Long logId, AuthorResponse author, LocalDateTime issuedDate, CategoryResponse category, String title, String content, List<String> tags) {
        this.logId = logId;
        this.author = author;
        this.issuedDate = issuedDate;
        this.category = category;
        this.title = title;
        this.content = content;
        this.tags = tags;
    }

    public Long getLogId() {
        return logId;
    }

    public AuthorResponse getAuthor() {
        return author;
    }

    public LocalDateTime getIssuedDate() {
        return issuedDate;
    }

    public CategoryResponse getCategory() {
        return category;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public List<String> getTags() {
        return tags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LogResponse that = (LogResponse) o;
        return Objects.equals(logId, that.logId) && Objects.equals(author, that.author) && Objects.equals(category, that.category) && Objects.equals(title, that.title) && Objects.equals(content, that.content) && Objects.equals(tags, that.tags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(logId, author, title, content, tags);
    }
}
