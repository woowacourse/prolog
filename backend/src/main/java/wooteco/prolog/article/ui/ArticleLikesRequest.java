package wooteco.prolog.article.ui;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ArticleLikesRequest {

    private final Boolean likes;

    public ArticleLikesRequest() {
        this(null);
    }
}
