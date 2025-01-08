package wooteco.prolog.article.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Objects;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@ToString
@Embeddable
public class ImageUrl {

    public static final int MAX_LENGTH = 1024;

    @Column(name = "image_url", nullable = false, length = MAX_LENGTH)
    private String url;

    public ImageUrl(String url) {
        if (Objects.isNull(url) || url.isEmpty() || url.trim().isEmpty() || url.length() > MAX_LENGTH) {
            url = "https://avatars.githubusercontent.com/u/45747236?s=200&v=4";
        }
        this.url = url.trim();
    }
}
