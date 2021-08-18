package wooteco.prolog.post.domain;

import java.util.Objects;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import wooteco.prolog.login.excetpion.PostTitleNullOrEmptyException;

@Getter
@EqualsAndHashCode
@ToString
public class Title {
    private final String title;

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
