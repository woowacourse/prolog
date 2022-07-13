package kr.co.techcourse.prolog.batch.job.updatepopularstudylogs.entity.studylog.repository;

import kr.co.techcourse.prolog.batch.job.updatepopularstudylogs.entity.studylog.PopularStudylog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PopularStudylogRepository extends JpaRepository<PopularStudylog, Long> {

}
