package wooteco.prolog.studylog.application.dto;

import java.util.Date;

public class RssFeedResponse {

    private final String title;
    private final String content;
    private final String author;
    private final String link;
    private final Date date;

    public RssFeedResponse(String title, String content, String author, String link, Date date) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.link = link;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getAuthor() {
        return author;
    }

    public String getLink() {
        return link;
    }

    public Date getDate() {
        return date;
    }
}
