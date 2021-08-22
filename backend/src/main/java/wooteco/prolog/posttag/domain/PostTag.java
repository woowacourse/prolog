package wooteco.prolog.posttag.domain;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wooteco.prolog.BaseEntity;
import wooteco.prolog.post.domain.Post;
import wooteco.prolog.tag.domain.Tag;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class PostTag extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "tag_id", nullable = false)
    private Tag tag;
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    public PostTag(Post post, Tag tag) {
        this(null, post, tag);
    }

    public PostTag(Long id, Post post, Tag tag) {
        super(id);
        this.post = post;
        this.tag = tag;
    }
}
