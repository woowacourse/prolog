package wooteco.prolog.article.domain;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import wooteco.prolog.article.exception.ArticleUrlNullOrEmptyException;
import wooteco.prolog.article.exception.ArticleUrlOverLengthException;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@ToString
@Embeddable
public class Url {

    public static final int MAX_LENGTH = 1024;

    @Column(nullable = false, length = MAX_LENGTH)
    private String url;

    public Url(String url) {
        validateNull(url);
        validateEmpty(url);
        validateOnlyBlank(url);
        validateMaxLength(url);
        this.url = trim(url);
    }

    private String trim(String name) {
        return name.trim();
    }

    private void validateNull(String url) {
        if (Objects.isNull(url)) {
            throw new ArticleUrlNullOrEmptyException();
        }
    }

    private void validateEmpty(String url) {
        if (url.isEmpty()) {
            throw new ArticleUrlNullOrEmptyException();
        }
    }

    private void validateOnlyBlank(String url) {
        if (url.trim().isEmpty()) {
            throw new ArticleUrlNullOrEmptyException();
        }
    }

    private void validateMaxLength(String url) {
        if (url.length() > MAX_LENGTH) {
            throw new ArticleUrlOverLengthException();
        }
    }
}
