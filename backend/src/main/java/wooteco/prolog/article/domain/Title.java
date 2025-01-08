package wooteco.prolog.article.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
            title = title.substring(0, MAX_LENGTH);
        }

        this.title = title.trim();
    }
}
