package wooteco.prolog.article.application;

public class RssFeedException extends RuntimeException {
    public RssFeedException(String message, Exception e) {
        super(message, e);
    }
}
