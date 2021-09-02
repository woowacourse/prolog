package wooteco.prolog.studylog.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class StudylogTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id", nullable = false)
    private Tag tag;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "studylog_id", nullable = false)
    private Studylog studylog;

    public StudylogTag(Studylog studylog, Tag tag) {
        this(null, studylog, tag);
    }

    public StudylogTag(Long id, Studylog studylog, Tag tag) {
        this.id = id;
        this.studylog = studylog;
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
        if (!(o instanceof StudylogTag)) {
            return false;
        }
        StudylogTag studylogTag = (StudylogTag) o;
        return Objects.equals(id, studylogTag.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
