package wooteco.prolog.studylog.domain;

import java.util.ArrayList;
import java.util.List;
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

    public StudylogTags(List<StudylogTag> values) {
        this.values = values;
    }

    public void add(List<StudylogTag> studylogTags) {
        values.addAll(duplicateFilter(studylogTags));
    }

    private List<StudylogTag> duplicateFilter(List<StudylogTag> studylogTags) {
        return studylogTags.stream()
            .filter(studylogTag -> values.stream().map(StudylogTag::getTag)
                .noneMatch(newTag -> newTag.isSameName(studylogTag.getTag())))
            .collect(Collectors.toList());
    }

    public void update(List<StudylogTag> studylogTags) {
        values.clear();
        values.addAll(studylogTags);
    }
}
