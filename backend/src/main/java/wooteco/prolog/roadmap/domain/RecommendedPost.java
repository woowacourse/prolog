package wooteco.prolog.roadmap.domain;

import static io.micrometer.core.instrument.util.StringUtils.isBlank;
import static java.util.Objects.hash;
import static java.util.Objects.isNull;
import static wooteco.prolog.common.exception.BadRequestCode.ROADMAP_KEYWORD_NOT_FOUND_EXCEPTION;
import static wooteco.prolog.common.exception.BadRequestCode.ROADMAP_RECOMMENDED_POST_INVALID_URL_LENGTH;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wooteco.prolog.common.exception.BadRequestException;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class RecommendedPost {

    public static final int URL_LENGTH_UPPER_BOUND = 512;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String url;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Keyword keyword;

    public RecommendedPost(final Long id, final String url, final Keyword keyword) {
        validate(url, keyword);

        this.id = id;
        this.url = url.trim();
        this.keyword = keyword;
    }

    private void validate(final String url, final Keyword keyword) {
        if (isNull(keyword)) {
            throw new BadRequestException(ROADMAP_KEYWORD_NOT_FOUND_EXCEPTION);
        }
        if (isBlank(url) || url.trim().length() > URL_LENGTH_UPPER_BOUND) {
            throw new BadRequestException(ROADMAP_RECOMMENDED_POST_INVALID_URL_LENGTH);
        }
    }

    public RecommendedPost(final String url, final Keyword keyword) {
        this(null, url, keyword);
    }

    public void updateUrl(final String url) {
        this.url = url;
    }

    public void remove() {
        keyword.getRecommendedPosts().remove(this);
    }

    public void addKeyword(final Keyword keyword) {
        this.keyword = keyword;
        keyword.getRecommendedPosts().add(this);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof RecommendedPost)) return false;
        final RecommendedPost post = (RecommendedPost) o;
        return Objects.equals(id, post.id);
    }

    @Override
    public int hashCode() {
        return hash(id);
    }
}
