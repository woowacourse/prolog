package wooteco.prolog.article.ui;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ArticleBookmarkRequest {

    private final Boolean bookmark;

    public ArticleBookmarkRequest() {
        this(null);
    }
}
