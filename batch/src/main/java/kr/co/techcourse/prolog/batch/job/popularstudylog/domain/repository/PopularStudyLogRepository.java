package kr.co.techcourse.prolog.batch.job.popularstudylog.domain.repository;

import java.util.List;
import kr.co.techcourse.prolog.batch.job.popularstudylog.domain.PopularStudyLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PopularStudyLogRepository extends JpaRepository<PopularStudyLog, Long> {

    @Query("select p from PopularStudyLog p where p.deleted = false")
    List<PopularStudyLog> findAll();
}
