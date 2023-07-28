package wooteco.prolog.article.ui;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private final LocalDateTime createdAt;

    private ArticleResponse() {
        this(null, null, null, null, null);
    }

    public static ArticleResponse from(final Article article) {
        final Long id = article.getId();
        final String nickName = article.getMember().getNickname();
        final String title = article.getTitle().getTitle();
        final String url = article.getUrl().getUrl();
        final LocalDateTime createdAt = article.getCreatedAt();
        return new ArticleResponse(id, nickName, title, url, createdAt);
    }
}
