package wooteco.prolog.post.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import wooteco.prolog.login.excetpion.PostTitleNullOrEmptyException;
import wooteco.prolog.post.exception.PostContentNullOrEmptyException;

import java.util.Objects;

@Getter
@EqualsAndHashCode
@ToString
public class Title {
    private final String title;

    public Title(String title) {
        String trimTitle = trim(title);
        validateNullOrEmpty(trimTitle);
        this.title = trim(trimTitle);
    }

    private String trim(String title) {
        if (Objects.nonNull(title)) {
            return title.trim();
        }
        return null;
    }

    private void validateNullOrEmpty(String title) {
        if (Objects.isNull(title) || title.isEmpty()) {
            throw new PostTitleNullOrEmptyException();
        }
    }
}
