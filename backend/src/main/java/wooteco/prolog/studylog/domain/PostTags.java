package wooteco.prolog.studylog.domain;

import lombok.Getter;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.hibernate.annotations.BatchSize;

@Getter
@Embeddable
public class PostTags {

    @OneToMany(mappedBy = "post", cascade = CascadeType.PERSIST, orphanRemoval = true)
    @BatchSize(size = 1000)
    private final List<PostTag> values;

    public PostTags() {
        this(new ArrayList<>());
    }

    public PostTags(List<PostTag> values) {
        this.values = values;
    }

    public void add(List<PostTag> postTags) {
        values.addAll(duplicateFilter(postTags));
    }

    private List<PostTag> duplicateFilter(List<PostTag> postTags) {
        return postTags.stream()
                .filter(postTag -> values.stream().map(PostTag::getTag)
                        .noneMatch(newTag -> newTag.isSameName(postTag.getTag())))
                .collect(Collectors.toList());
    }

    public void update(List<PostTag> postTags) {
        values.clear();
        values.addAll(postTags);
    }
}
