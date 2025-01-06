package wooteco.prolog.article.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import wooteco.prolog.article.application.OgTagParser.OgType;
import wooteco.prolog.article.application.dto.ArticleUrlRequest;
import wooteco.prolog.article.application.dto.ArticleUrlResponse;

import java.util.Map;

@RequiredArgsConstructor
@Service
public class MetaOgService {

    private final OgTagParser ogTagParser;

    public ArticleUrlResponse parse(final ArticleUrlRequest articleUrlRequest) {
        final Map<OgType, String> parse = ogTagParser.parse(articleUrlRequest.getUrl());

        return ArticleUrlResponse.from(parse);
    }
}
