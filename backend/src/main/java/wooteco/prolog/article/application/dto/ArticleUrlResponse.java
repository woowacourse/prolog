package wooteco.prolog.article.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import wooteco.prolog.article.application.OgTagParser.OgType;

import java.util.Map;

@Getter
@AllArgsConstructor
public class ArticleUrlResponse {
    private final String title;
    private final String description;
    private final String imageUrl;

    public static ArticleUrlResponse from(final Map<OgType, String> parsedTags) {
        final String title = parsedTags.get(OgType.TITLE);
        final String description = parsedTags.get(OgType.DESCRIPTION);
        final String image = parsedTags.get(OgType.IMAGE);
        return new ArticleUrlResponse(title, description, image);
    }
}


