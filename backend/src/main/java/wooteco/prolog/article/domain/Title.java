package wooteco.prolog.article.domain;

import static wooteco.prolog.common.exception.BadRequestCode.ARTICLE_TITLE_NULL_OR_EMPTY_EXCEPTION;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.commons.text.StringEscapeUtils;
import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import wooteco.prolog.common.exception.BadRequestCode;
import wooteco.prolog.common.exception.BadRequestException;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@ToString
@Embeddable
public class Title {

    public static final int MAX_LENGTH = 50;

    @Column(nullable = false, length = MAX_LENGTH)
    private String title;

    public Title(String title) {
        validateNull(title);
        validateEmpty(title);
        validateOnlyBlank(title);
        validateMaxLength(title);
        this.title = trim(title);
    }

    private String trim(String name) {
        // HTML 태그와 자바스크립트를 제거, HTML 특수 문자를 변환
        String result = StringEscapeUtils.unescapeHtml4(Jsoup.clean(name, Safelist.none()));
        return result.trim();
    }

    private void validateNull(String title) {
        if (Objects.isNull(title)) {
            throw new BadRequestException(ARTICLE_TITLE_NULL_OR_EMPTY_EXCEPTION);
        }
    }

    private void validateEmpty(String title) {
        if (title.isEmpty()) {
            throw new BadRequestException(ARTICLE_TITLE_NULL_OR_EMPTY_EXCEPTION);
        }
    }

    private void validateOnlyBlank(String title) {
        if (title.trim().isEmpty()) {
            throw new BadRequestException(ARTICLE_TITLE_NULL_OR_EMPTY_EXCEPTION);
        }
    }

    private void validateMaxLength(String title) {
        if (title.length() > MAX_LENGTH) {
            throw new BadRequestException(BadRequestCode.ARTICLE_TITLE_OVER_LENGTH_EXCEPTION);
        }
    }
}
