package wooteco.prolog.article.domain;

import static java.lang.Boolean.TRUE;

import java.time.LocalDateTime;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import wooteco.prolog.common.exception.BadRequestCode;
import wooteco.prolog.common.exception.BadRequestException;
import wooteco.prolog.member.domain.Member;

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

    @CreatedDate
    private LocalDateTime createdAt;

    public Article(final Member member, final Title title, final Url url, final ImageUrl imageUrl) {
        this.member = member;
        this.title = title;
        this.url = url;
        this.imageUrl = imageUrl;
        this.articleBookmarks = new ArticleBookmarks();
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
}
