package wooteco.prolog.post.domain;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import wooteco.prolog.login.excetpion.PostTitleNullOrEmptyException;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@ToString
public class Title {
    @Column
    private String title;

    public Title(String title) {
        validateNull(title);
        validateEmpty(title);
        validateOnlyBlank(title);
        this.title = trim(title);
    }

    private String trim(String name) {
        return name.trim();
    }

    private void validateNull(String title) {
        if (Objects.isNull(title)) {
            throw new PostTitleNullOrEmptyException();
        }
    }

    private void validateEmpty(String title) {
        if (title.isEmpty()) {
            throw new PostTitleNullOrEmptyException();
        }
    }

    private void validateOnlyBlank(String title) {
        if (title.trim().isEmpty()) {
            throw new PostTitleNullOrEmptyException();
        }
    }
}
