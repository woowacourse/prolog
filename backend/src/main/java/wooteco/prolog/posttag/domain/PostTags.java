package wooteco.prolog.posttag.domain;

import static java.util.stream.Collectors.groupingBy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import lombok.Getter;
import wooteco.prolog.tag.domain.Tag;

@Getter
@Embeddable
public class PostTags {

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
    private final List<PostTag> values = new ArrayList<>();

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

    public Map<Tag, Long> groupingWithCounting() {
        return values.stream()
                .collect(groupingBy(PostTag::getTag, Collectors.counting()));
    }
}
