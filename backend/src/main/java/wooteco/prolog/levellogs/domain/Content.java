package wooteco.prolog.levellogs.domain;

import static wooteco.prolog.common.exception.BadRequestCode.STUDYLOG_CONTENT_NULL_OR_EMPTY;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import wooteco.prolog.common.exception.BadRequestException;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
        if (title != null) {
            return title.trim().length();
        }
        return 0;
    }

    private void validateNullOrEmpty(String content, int trimedContentLength) {
        if ((content == null) || trimedContentLength == 0) {
            throw new BadRequestException(STUDYLOG_CONTENT_NULL_OR_EMPTY);
        }
    }
}
