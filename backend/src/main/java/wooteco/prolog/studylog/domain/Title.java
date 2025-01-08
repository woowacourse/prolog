package wooteco.prolog.studylog.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import wooteco.prolog.common.exception.BadRequestException;

import java.util.Objects;

import static wooteco.prolog.common.exception.BadRequestCode.STUDYLOG_TITLE_NULL_OR_EMPTY;
import static wooteco.prolog.common.exception.BadRequestCode.STUDYLOG_TITLE_TOO_LONG;

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
        return name.trim();
    }

    private void validateNull(String title) {
        if (Objects.isNull(title)) {
            throw new BadRequestException(STUDYLOG_TITLE_NULL_OR_EMPTY);
        }
    }

    private void validateEmpty(String title) {
        if (title.isEmpty()) {
            throw new BadRequestException(STUDYLOG_TITLE_NULL_OR_EMPTY);
        }
    }

    private void validateOnlyBlank(String title) {
        if (title.trim().isEmpty()) {
            throw new BadRequestException(STUDYLOG_TITLE_NULL_OR_EMPTY);
        }
    }

    private void validateMaxLength(String title) {
        if (title.length() > MAX_LENGTH) {
            throw new BadRequestException(STUDYLOG_TITLE_TOO_LONG);
        }
    }
}
