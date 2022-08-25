package kr.co.techcourse.prolog.batch.job.popularstudylog.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class PopularStudyLog {

    @Id
    @Column(updatable = false)
    private Long id;

    @Column(nullable = false, updatable = false)
    private Long studylogId;

    @Column(nullable = false)
    private boolean deleted;

    protected PopularStudyLog() { }

    public PopularStudyLog(Long studylogId) {
        this.studylogId = studylogId;
        this.deleted = false;
    }

    public void delete() {
        this.deleted = true;
    }
}
