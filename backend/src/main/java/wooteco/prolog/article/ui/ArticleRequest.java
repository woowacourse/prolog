package wooteco.prolog.article.ui;

import lombok.AllArgsConstructor;
import lombok.Getter;
import wooteco.prolog.article.domain.Article;
import wooteco.prolog.article.domain.ImageUrl;
import wooteco.prolog.article.domain.Title;
import wooteco.prolog.article.domain.Url;
import wooteco.prolog.member.domain.Member;

@Getter
@AllArgsConstructor
public class ArticleRequest {

    private final String title;
    private final String url;
    private final String imageUrl;

    public Article toArticle(final Member member) {
        return new Article(member, new Title(title), new Url(url), new ImageUrl(imageUrl));
    }
}
