package wooteco.prolog.image.application.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ImageUrlResponse {

    private String url;

    public ImageUrlResponse(final String url) {
        this.url = url;
    }
}
