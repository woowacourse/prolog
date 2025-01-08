package wooteco.prolog.article.domain;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.commons.lang3.StringEscapeUtils;
import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@ToString
@Embeddable
public class Description {

    private String description;

    public Description(String description) {
        if (description == null || description.isEmpty() || description.trim().isEmpty()) {
            description = "내용없음";
        }
        if (description.length() > 200) {
            description = description.substring(0, 200);
        }

        this.description = StringEscapeUtils.unescapeHtml4(Jsoup.clean(description, Safelist.none()));
    }
}
