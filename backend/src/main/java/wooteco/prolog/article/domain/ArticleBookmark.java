package wooteco.prolog.article.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "article_bookmark")
public class ArticleBookmark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    private Article article;

    private Long memberId;

    private ArticleBookmark(final Long id, final Article article, final Long memberId) {
        this.id = id;
        this.article = article;
        this.memberId = memberId;
    }

    public ArticleBookmark(final Article article, final Long memberId) {
        this(null, article, memberId);
    }

    public boolean isOwner(final Long memberId) {
        return Objects.equals(this.memberId, memberId);
    }

    public Long getMemberId() {
        return memberId;
    }
}
