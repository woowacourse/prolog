package kr.co.techcourse.prolog.batch.job.updatepopularstudylogs.entity.studylog;

import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "likes")
@Entity
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    protected Like() {
    }

    public Long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Like)) {
            return false;
        }
        Like like = (Like) o;
        return Objects.equals(getId(), like.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}