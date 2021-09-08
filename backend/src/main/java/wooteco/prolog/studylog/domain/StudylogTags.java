package wooteco.prolog.studylog.domain;

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

@Getter
@Embeddable
public class StudylogTags {

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
    private final List<StudylogTag> values = new ArrayList<>();

    public void add(List<StudylogTag> studylogTags) {
        values.addAll(duplicateFilter(studylogTags));
    }

    private List<StudylogTag> duplicateFilter(List<StudylogTag> studylogTags) {
        return studylogTags.stream()
            .filter(postTag -> values.stream().map(StudylogTag::getTag)
                .noneMatch(newTag -> newTag.isSameName(postTag.getTag())))
            .collect(Collectors.toList());
    }

    public void update(List<StudylogTag> studylogTags) {
        values.clear();
        values.addAll(studylogTags);
    }

    public Map<Tag, Long> groupingWithCounting() {
        return values.stream()
                .collect(groupingBy(StudylogTag::getTag, Collectors.counting()));
    }
}
