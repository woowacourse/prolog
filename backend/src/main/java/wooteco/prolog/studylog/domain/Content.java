package wooteco.prolog.studylog.domain;


import lombok.*;
import wooteco.prolog.studylog.exception.PostContentNullOrEmptyException;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

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
            throw new PostContentNullOrEmptyException();
        }
    }
}
