package wooteco.prolog.article.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import wooteco.prolog.common.exception.BadRequestCode;
import wooteco.prolog.common.exception.BadRequestException;
import wooteco.prolog.member.domain.Member;

import javax.persistence.*;
import java.time.LocalDateTime;

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

    @CreatedDate
    private LocalDateTime createdAt;

    public Article(final Member member, final Title title, final Url url, final ImageUrl imageUrl) {
        this.member = member;
        this.title = title;
        this.url = url;
        this.imageUrl = imageUrl;
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
}
