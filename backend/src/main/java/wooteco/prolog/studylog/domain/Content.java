package wooteco.prolog.studylog.domain;


import static wooteco.prolog.common.exception.BadRequestCode.STUDYLOG_CONTENT_NULL_OR_EMPTY;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import wooteco.prolog.common.exception.BadRequestException;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@ToString
@Embeddable
public class Content {

    @Column(nullable = false, columnDefinition = "text")
    private String content;

    public Content(String content) {
        validateNullOrEmpty(content, length(content));
        this.content = content;
    }

    private int length(String title) {
        if (Objects.nonNull(title)) {
            return title.trim().length();
        }
        return 0;
    }

    private void validateNullOrEmpty(String content, int trimedContentLength) {
        if (Objects.isNull(content) || trimedContentLength == 0) {
            throw new BadRequestException(STUDYLOG_CONTENT_NULL_OR_EMPTY);
        }
    }
}
