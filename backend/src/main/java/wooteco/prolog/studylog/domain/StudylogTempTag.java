package wooteco.prolog.studylog.domain;

import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class StudylogTempTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id")
    private Tag tag;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "studylog_temp_id", nullable = false)
    private StudylogTemp studylogTemp;

    public StudylogTempTag(StudylogTemp studylogTemp, Tag tag) {
        this(null, studylogTemp, tag);
    }

    public StudylogTempTag(Long id, StudylogTemp studylogTemp, Tag tag) {
        this.id = id;
        this.studylogTemp = studylogTemp;
        this.tag = tag;
    }

    public Long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StudylogTempTag)) {
            return false;
        }
        StudylogTempTag studylogTempTag = (StudylogTempTag) o;
        return Objects.equals(id, studylogTempTag.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
