package wooteco.prolog.post.domain;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import wooteco.prolog.post.exception.PostArgumentNullOrEmptyException;

import java.util.Objects;

@Getter
@EqualsAndHashCode
@ToString
public class Content {
    private final String content;

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
            throw new PostArgumentNullOrEmptyException("내용은 공백일 수 없습니다.");
        }
    }
}
