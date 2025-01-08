package wooteco.prolog.studylog.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;

import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "LIKES")
@Entity
@BatchSize(size = 1000)
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "studylog_id")
    private Studylog studylog;

    private Long memberId;

    public Like(Studylog studylog, Long memberId) {
        this.studylog = studylog;
        this.memberId = memberId;
    }

    public Long getId() {
        return id;
    }

    public Long getMemberId() {
        return memberId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Like like = (Like) o;
        return Objects.equals(getMemberId(), like.getMemberId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMemberId());
    }
}
