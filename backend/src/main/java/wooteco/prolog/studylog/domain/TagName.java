package wooteco.prolog.studylog.domain;

import static wooteco.prolog.common.exception.BadRequestCode.TAG_NAME_NULL_OR_EMPTY;
import static wooteco.prolog.common.exception.BadRequestCode.TOO_LONG_TAG_NAME;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import wooteco.prolog.common.exception.BadRequestException;

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
            throw new BadRequestException(TOO_LONG_TAG_NAME);
        }
    }

    private String trim(String name) {
        return name.trim();
    }

    private void validateNull(String name) {
        if (Objects.isNull(name)) {
            throw new BadRequestException(TAG_NAME_NULL_OR_EMPTY);
        }
    }

    private void validateEmpty(String name) {
        if (name.isEmpty()) {
            throw new BadRequestException(TAG_NAME_NULL_OR_EMPTY);
        }
    }

    private void validateOnlyBlank(String name) {
        if (name.trim().isEmpty()) {
            throw new BadRequestException(TAG_NAME_NULL_OR_EMPTY);
        }
    }

    public String getValue() {
        return value;
    }
}
