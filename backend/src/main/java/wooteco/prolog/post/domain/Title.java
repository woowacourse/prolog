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
import wooteco.prolog.post.exception.TooLongTitleException;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@ToString
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

    private void validateMaxLength(String title) {
        if (title.length() > MAX_LENGTH) {
            throw new TooLongTitleException();
        }
    }
}
