package wooteco.prolog.article.application.dto;

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

