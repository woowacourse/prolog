package wooteco.prolog.article.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import wooteco.prolog.common.exception.BadRequestCode;
import wooteco.prolog.common.exception.BadRequestException;
import wooteco.prolog.member.domain.Member;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

import static java.lang.Boolean.TRUE;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Embedded
    private Title title;

    @Embedded
    private Url url;

    @Embedded
    private ImageUrl imageUrl;

    @Embedded
    private ArticleBookmarks articleBookmarks;

    @Embedded
    private ArticleLikes articleLikes;

    @CreatedDate
    private LocalDateTime createdAt;

    @Embedded
    private ViewCount views;

    public Article(final Member member, final Title title, final Url url, final ImageUrl imageUrl) {
        this.member = member;
        this.title = title;
        this.url = url;
        this.imageUrl = imageUrl;
        this.articleBookmarks = new ArticleBookmarks();
        this.articleLikes = new ArticleLikes();
        this.views = new ViewCount();
    }

    public void validateOwner(final Member member) {
        if (!member.equals(this.member)) {
            throw new BadRequestException(BadRequestCode.INVALID_ARTICLE_AUTHOR_EXCEPTION);
        }
    }

    public void update(final String title, final String url) {
        this.title = new Title(title);
        this.url = new Url(url);
    }

    public void setBookmark(final Member member, final Boolean isBookmark) {
        if (TRUE.equals(isBookmark)) {
            addBookmark(member);
        } else {
            removeBookmark(member);
        }
    }

    private void addBookmark(final Member member) {
        final ArticleBookmark articleBookmark = new ArticleBookmark(this, member.getId());
        articleBookmarks.addBookmark(articleBookmark);
    }

    private void removeBookmark(final Member member) {
        articleBookmarks.removeBookmark(member.getId());
    }

    public void setLike(final Member member, final Boolean isLike) {
        if (TRUE.equals(isLike)) {
            addLike(member);
        } else {
            removeLike(member);
        }
    }

    private void addLike(final Member member) {
        final ArticleLike articleLike = new ArticleLike(this, member.getId());
        articleLikes.addLike(articleLike);
    }

    private void removeLike(final Member member) {
        articleLikes.removeLike(member.getId());
    }

    public void updateViewCount() {
        this.views.increase();
    }
}
