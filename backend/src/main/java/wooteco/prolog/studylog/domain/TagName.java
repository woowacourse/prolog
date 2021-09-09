package wooteco.prolog.studylog.domain;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import wooteco.prolog.studylog.exception.TooLongTagNameException;

@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class TagName {

    public static final int MAX_LENGTH = 20;

    @Column(name = "name", nullable = false, length = MAX_LENGTH)
    private String value;

    public TagName(String name) {
        validateNull(name);
        validateEmpty(name);
        validateOnlyBlank(name);
        validateMaxLength(trim(name));
        this.value = trim(name);
    }

    private void validateMaxLength(String name) {
        if (name.length() > MAX_LENGTH) {
            throw new TooLongTagNameException();
        }
    }

    private String trim(String name) {
        return name.trim();
    }

    private void validateNull(String name) {
        if (Objects.isNull(name)) {
            throw new wooteco.prolog.studylog.exception.TagNameNullOrEmptyException();
        }
    }

    private void validateEmpty(String name) {
        if (name.isEmpty()) {
            throw new wooteco.prolog.studylog.exception.TagNameNullOrEmptyException();
        }
    }

    private void validateOnlyBlank(String name) {
        if (name.trim().isEmpty()) {
            throw new wooteco.prolog.studylog.exception.TagNameNullOrEmptyException();
        }
    }

    public String getValue() {
        return value;
    }
}
