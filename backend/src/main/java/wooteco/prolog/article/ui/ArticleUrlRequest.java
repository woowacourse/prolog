package wooteco.prolog.article.ui;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ArticleUrlRequest {

    private final String url;

    private ArticleUrlRequest() {
        this(null);
    }
}

