package wooteco.prolog.studylog.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import lombok.Getter;
import org.hibernate.annotations.BatchSize;

@Getter
@Embeddable
public class StudylogTempTags {

    @OneToMany(mappedBy = "studylogTemp", cascade = CascadeType.PERSIST, orphanRemoval = true)
    @BatchSize(size = 1000)
    private final List<StudylogTempTag> values;

    public StudylogTempTags() {
        this(new ArrayList<>());
    }

    public StudylogTempTags(List<StudylogTempTag> values) {
        this.values = values;
    }
}
