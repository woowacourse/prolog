package wooteco.prolog.comment.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wooteco.prolog.comment.exception.CommentNullOrEmptyException;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Embeddable
public class Content {

    @Column(nullable = false, columnDefinition = "text")
    private String content;

    public Content(final String content) {
        validateNullOrEmpty(content, length(content));
        this.content = content;
    }

    private int length(final String value) {
        if (value != null) {
            return value.trim().length();
        }
        return 0;
    }

    private void validateNullOrEmpty(final String content, final int trimContentLength) {
        if ((content == null) || trimContentLength == 0) {
            throw new CommentNullOrEmptyException();
        }
    }
}
