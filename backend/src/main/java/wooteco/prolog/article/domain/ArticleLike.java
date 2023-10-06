package wooteco.prolog.article.domain;

import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
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
