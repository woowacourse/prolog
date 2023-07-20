package wooteco.prolog.article.ui;

import lombok.AllArgsConstructor;
import lombok.Getter;
import wooteco.prolog.article.domain.Article;

@AllArgsConstructor
@Getter
public class ArticleResponse {

    private final Long id;
    private final String userName;
    private final String title;
    private final String url;
    private final String createdAt;

    private ArticleResponse() {
        this(null, null, null, null, null);
    }

    public static ArticleResponse from(final Article article) {
        final Long id = article.getId();
        final String nickName = article.getMember().getNickname();
        final String title = article.getTitle().getTitle();
        final String url = article.getUrl().getUrl();
        final String createdAt = article.getCreatedAt().toString();

        return new ArticleResponse(id, nickName, title, url, createdAt);
    }
}
