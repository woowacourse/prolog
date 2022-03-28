package wooteco.prolog.studylog.domain.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import wooteco.prolog.studylog.domain.MostPopularStudylog;

public interface MostPopularStudylogRepository extends JpaRepository<MostPopularStudylog, Long> {

    List<MostPopularStudylog> findAllByDeletedFalse();
}
