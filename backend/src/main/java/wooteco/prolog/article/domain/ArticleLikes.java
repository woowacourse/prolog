package wooteco.prolog.article.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Embeddable
public class ArticleLikes {

    @OneToMany(mappedBy = "article")
    @Cascade(value = {CascadeType.PERSIST, CascadeType.DELETE})
    private List<ArticleLike> articleLikes;

    public ArticleLikes() {
        this.articleLikes = new ArrayList<>();
    }

    public void addLike(final ArticleLike articleLike) {
        articleLikes.add(articleLike);
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
}
