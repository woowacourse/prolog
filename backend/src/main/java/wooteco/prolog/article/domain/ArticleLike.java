package wooteco.prolog.article.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "article_like")
public class ArticleLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    private Article article;

    private Long memberId;

    private ArticleLike(final Long id, final Article article, final Long memberId) {
        this.id = id;
        this.article = article;
        this.memberId = memberId;
    }

    public ArticleLike(final Article article, final Long memberId) {
        this(null, article, memberId);
    }

    public boolean isOwner(final Long memberId) {
        return Objects.equals(this.memberId, memberId);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ArticleLike that = (ArticleLike) o;
        return Objects.equals(id, that.id) ||
            (Objects.equals(article, that.article)
                && Objects.equals(memberId, that.memberId));
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, article, memberId);
    }
}
