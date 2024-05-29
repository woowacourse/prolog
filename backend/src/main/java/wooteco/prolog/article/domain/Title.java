package wooteco.prolog.article.domain;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.commons.text.StringEscapeUtils;
import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

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
        if (Objects.isNull(title) || title.isEmpty() || title.trim().isEmpty()) {
            title = "제목없음";
        }
        if (title.length() > MAX_LENGTH) {
            title = title.substring(0, MAX_LENGTH - 1);
        }
        this.title = trim(title);
    }

    private String trim(String name) {
        // HTML 태그와 자바스크립트를 제거, HTML 특수 문자를 변환
        String result = StringEscapeUtils.unescapeHtml4(Jsoup.clean(name, Safelist.none()));
        return result.trim();
    }
}
