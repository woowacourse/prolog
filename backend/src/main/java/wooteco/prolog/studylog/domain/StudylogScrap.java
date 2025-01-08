package wooteco.prolog.studylog.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.util.Objects;
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
        if (!(o instanceof StudylogScrap studylogScrap)) {
            return false;
        }
        return Objects.equals(member.getId(), studylogScrap.member.getId()) &&
            Objects.equals(studylog.getId(), studylogScrap.getStudylog().getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(member.getId(), studylog.getId());
    }
}
