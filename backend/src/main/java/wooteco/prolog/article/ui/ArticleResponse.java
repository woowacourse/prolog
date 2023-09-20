package wooteco.prolog.article.ui;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import wooteco.prolog.article.domain.Article;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class ArticleResponse {

    private final Long id;
    private final String userName;
    private final String title;
    private final String url;
    private final String imageUrl;
    private final boolean isBookmarked;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private final LocalDateTime createdAt;

    private ArticleResponse() {
        this(null, null, null, null, null, false, null);
    }

    public static ArticleResponse of(final Article article, final Long memberId) {
        final Long id = article.getId();
        final String nickName = article.getMember().getNickname();
        final String title = article.getTitle().getTitle();
        final String url = article.getUrl().getUrl();
        final String imageUrl = article.getImageUrl().getUrl();
        final boolean isBookmarked = article.getArticleBookmarks().containBookmark(memberId);
        final LocalDateTime createdAt = article.getCreatedAt();
        return new ArticleResponse(id, nickName, title, url, imageUrl, isBookmarked, createdAt);
    }
}
