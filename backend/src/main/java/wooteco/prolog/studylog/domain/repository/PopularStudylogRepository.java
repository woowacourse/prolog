package wooteco.prolog.studylog.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wooteco.prolog.studylog.domain.PopularStudylog;

import java.util.List;

public interface PopularStudylogRepository extends JpaRepository<PopularStudylog, Long> {

    List<PopularStudylog> findAllByDeletedFalse();
}
