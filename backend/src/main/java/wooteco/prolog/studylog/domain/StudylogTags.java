package wooteco.prolog.studylog.domain;

import static java.util.stream.Collectors.groupingBy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import lombok.Getter;
import org.hibernate.annotations.BatchSize;

@Getter
@Embeddable
public class StudylogTags {

    @OneToMany(mappedBy = "studylog", cascade = CascadeType.PERSIST, orphanRemoval = true)
    @BatchSize(size = 1000)
    private final List<StudylogTag> values;

    public StudylogTags() {
        this(new ArrayList<>());
    }

    public StudylogTags(List<wooteco.prolog.studylog.domain.StudylogTag> values) {
        this.values = values;
    }

    public void add(List<StudylogTag> studylogTags) {
        values.addAll(duplicateFilter(studylogTags));
    }

    private List<wooteco.prolog.studylog.domain.StudylogTag> duplicateFilter(
        List<wooteco.prolog.studylog.domain.StudylogTag> studylogTags) {
        return studylogTags.stream()
            .filter(studylogTag -> values.stream()
                .map(wooteco.prolog.studylog.domain.StudylogTag::getTag)
                .noneMatch(newTag -> newTag.isSameName(studylogTag.getTag())))
            .collect(Collectors.toList());
    }

    public void update(List<wooteco.prolog.studylog.domain.StudylogTag> studylogTags) {
        values.clear();
        values.addAll(studylogTags);
    }

    public Map<Tag, Long> groupingWithCounting() {
        return values.stream()
            .collect(groupingBy(wooteco.prolog.studylog.domain.StudylogTag::getTag,
                                Collectors.counting()));
    }

    public List<Long> getTagIds() {
        return values.stream()
            .map(value -> value.getTag().getId())
            .collect(Collectors.toList());
    }
}
