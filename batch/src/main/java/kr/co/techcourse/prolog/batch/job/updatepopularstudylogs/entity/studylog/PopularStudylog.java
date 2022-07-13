package kr.co.techcourse.prolog.batch.job.updatepopularstudylogs.entity.studylog;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * entity는 테이블과 꼭 완전히 맵핑되지 않아도 괜찮습니다.
 * @author 손너잘
 * @see Studylog's comment
 */
@Table(name = "popular_studylog")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class PopularStudylog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long studylogId;

    @Column(nullable = false)
    private boolean deleted;

    @Column(nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    protected PopularStudylog() {
    }

    public PopularStudylog(Long studylogId) {
        this.studylogId = studylogId;
        this.deleted = false;
    }

    public void delete() {
        this.deleted = true;
    }

    public Long getId() {
        return id;
    }

    public Long getStudylogId() {
        return studylogId;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}

