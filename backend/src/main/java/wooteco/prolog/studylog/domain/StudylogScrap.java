package wooteco.prolog.studylog.domain;

import java.util.Objects;
import javax.persistence.CascadeType;
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
import wooteco.prolog.member.domain.Member;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class StudylogScrap {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "studylog_id", nullable = false)
    private Studylog studylog;

    public StudylogScrap(Member member, Studylog studylog) {
        this(null, member, studylog);
    }

    public StudylogScrap(Long id, Member member, Studylog studylog) {
        this.id = id;
        this.member = member;
        this.studylog = studylog;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StudylogScrap)) {
            return false;
        }
        StudylogScrap studylogScrap = (StudylogScrap) o;
        return Objects.equals(member.getId(), studylogScrap.member.getId()) &&
            Objects.equals(studylog.getId(), studylogScrap.getStudylog().getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(member.getId(), studylog.getId());
    }
}