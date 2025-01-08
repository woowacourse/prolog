package wooteco.prolog.article.domain;

import jakarta.persistence.Embeddable;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Embeddable
public class ArticleLikes {

    @OneToMany(mappedBy = "article", orphanRemoval = true)
    @Cascade(value = CascadeType.PERSIST)
    private List<ArticleLike> articleLikes;

    public ArticleLikes() {
        this.articleLikes = new ArrayList<>();
    }

    public void addLike(final ArticleLike articleLike) {
        if (isNotAlreadyAdded(articleLike)) {
            articleLikes.add(articleLike);
        }
    }

    private boolean isNotAlreadyAdded(final ArticleLike articleLike) {
        return articleLikes.stream()
            .noneMatch(like -> like.equals(articleLike));
    }

    public void removeLike(final Long memberId) {
        articleLikes.stream()
            .filter(like -> like.isOwner(memberId))
            .findAny()
            .ifPresent(like -> articleLikes.remove(like));
    }

    public boolean isAlreadyLike(final Long memberId) {
        return articleLikes.stream()
            .anyMatch(like -> like.isOwner(memberId));
    }

    public List<ArticleLike> getArticleLikes() {
        return articleLikes;
    }
}
