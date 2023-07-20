package wooteco.prolog.roadmap.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class RecommendedPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String url;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Keyword keyword;

    public RecommendedPost(final String url) {
        this(null, url, null);
    }

    public void updateUrl(final String url) {
        this.url = url;
    }

    public void remove() {
        if (this.keyword == null) {
            return;
        }

        keyword.getRecommendedPosts().remove(this);
        this.keyword = null;
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
        return Objects.hash(id);
    }
}
