package wooteco.prolog.studylog.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

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
        if (!(o instanceof StudylogTempTag studylogTempTag)) {
            return false;
        }
        return Objects.equals(id, studylogTempTag.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
