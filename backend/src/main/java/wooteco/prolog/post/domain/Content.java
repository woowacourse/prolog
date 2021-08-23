package wooteco.prolog.post.domain;


import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import wooteco.prolog.post.exception.PostContentNullOrEmptyException;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@ToString
@Embeddable
public class Content {

    @Column(nullable = false)
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
