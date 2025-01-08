package wooteco.prolog.article.domain;

import static wooteco.prolog.common.exception.BadRequestCode.ARTICLE_URL_OVER_LENGTH_EXCEPTION;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import wooteco.prolog.common.exception.BadRequestCode;
import wooteco.prolog.common.exception.BadRequestException;

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
            throw new BadRequestException(BadRequestCode.ARTICLE_URL_NULL_OR_EMPTY_EXCEPTION);
        }
    }

    private void validateEmpty(String url) {
        if (url.isEmpty()) {
            throw new BadRequestException(BadRequestCode.ARTICLE_URL_NULL_OR_EMPTY_EXCEPTION);
        }
    }

    private void validateOnlyBlank(String url) {
        if (url.trim().isEmpty()) {
            throw new BadRequestException(BadRequestCode.ARTICLE_URL_NULL_OR_EMPTY_EXCEPTION);
        }
    }

    private void validateMaxLength(String url) {
        if (url.length() > MAX_LENGTH) {
            throw new BadRequestException(ARTICLE_URL_OVER_LENGTH_EXCEPTION);
        }
    }

    public boolean isSame(String url) {
        return this.url.equals(url);
    }
}
