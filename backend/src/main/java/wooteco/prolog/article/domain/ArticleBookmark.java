package wooteco.prolog.article.domain;

import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "article_bookmark")
public class ArticleBookmark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long articleId;

    private Long memberId;

    private ArticleBookmark(final Long id, final Long articleId, final Long memberId) {
        this.id = id;
        this.articleId = articleId;
        this.memberId = memberId;
    }

    public ArticleBookmark(final Long articleId, final Long memberId) {
        this(null, articleId, memberId);
    }

    public boolean isOwner(final Long memberId) {
        return Objects.equals(this.memberId, memberId);
    }
}
