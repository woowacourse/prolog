package kr.co.techcourse.prolog.batch.job.popularstudylog.domain;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "popular_studylog")
@Entity
public class PopularStudyLog extends AuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, updatable = false)
    private Long studylogId;

    @Column(nullable = false)
    private boolean deleted;

    protected PopularStudyLog() { }

    public PopularStudyLog(Long studylogId) {
        this(null, studylogId, false);
    }

    private PopularStudyLog(Long id, Long studylogId, boolean deleted) {
        this.id = id;
        this.studylogId = studylogId;
        this.deleted = deleted;
    }

    public void delete() {
        this.deleted = true;
    }
}
