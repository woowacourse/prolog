package kr.co.techcourse.prolog.batch.job.updatepopularstudylogs.entity.studylog.repository;

import kr.co.techcourse.prolog.batch.job.updatepopularstudylogs.entity.studylog.Studylog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudylogRepository extends JpaRepository<Studylog, Long> {

}
