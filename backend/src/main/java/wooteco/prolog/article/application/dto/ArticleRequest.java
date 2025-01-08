package wooteco.prolog.article.application.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wooteco.prolog.article.domain.Article;
import wooteco.prolog.article.domain.Description;
import wooteco.prolog.article.domain.ImageUrl;
import wooteco.prolog.article.domain.Title;
import wooteco.prolog.article.domain.Url;
import wooteco.prolog.member.domain.Member;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ArticleRequest {

    private String title;
    private String url;
    private String imageUrl;
    private String description;

    public ArticleRequest(String title, String url, String imageUrl) {
        this.title = title;
        this.url = url;
        this.imageUrl = imageUrl;
        this.description = "";
    }

    public Article toArticle(final Member member) {
        return new Article(member, new Title(title), new Description(description), new Url(url), new ImageUrl(imageUrl),
            LocalDateTime.now());
    }
}
