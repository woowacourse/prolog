package wooteco.prolog.article.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Embeddable
public class ArticleBookmarks {

    @OneToMany(mappedBy = "article")
    @Cascade(value = {CascadeType.PERSIST, CascadeType.DELETE})
    private List<ArticleBookmark> articleBookmarks;

    public ArticleBookmarks() {
        articleBookmarks = new ArrayList<>();
    }

    public void addBookmark(final ArticleBookmark bookmark) {
        if (isNotAlreadyAdded(bookmark)) {
            articleBookmarks.add(bookmark);
        }
    }

    private boolean isNotAlreadyAdded(final ArticleBookmark newBookmark) {
        return articleBookmarks.stream()
            .noneMatch(bookmark -> bookmark.isOwner(newBookmark.getMemberId()));
    }

    public void removeBookmark(final Long memberId) {
        articleBookmarks.stream()
            .filter(bookmark -> bookmark.isOwner(memberId))
            .findAny()
            .ifPresent(bookmark -> articleBookmarks.remove(bookmark));
    }

    public boolean containBookmark(final Long memberId) {
        return articleBookmarks.stream()
            .anyMatch(bookmark -> bookmark.isOwner(memberId));
    }

    public List<ArticleBookmark> getArticleBookmarks() {
        return articleBookmarks;
    }
}
