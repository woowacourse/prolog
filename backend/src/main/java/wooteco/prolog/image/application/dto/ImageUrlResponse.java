package wooteco.prolog.image.application.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ImageUrlResponse {

    private String imageUrl;

    public ImageUrlResponse(final String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
