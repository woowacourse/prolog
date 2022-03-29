package wooteco.prolog.studylog.domain.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import wooteco.prolog.studylog.domain.PopularStudylog;

public interface PopularStudylogRepository extends JpaRepository<PopularStudylog, Long> {

    List<PopularStudylog> findAllByDeletedFalse();
}
