package wooteco.prolog.studylog.domain.repository;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import wooteco.prolog.studylog.domain.Studylog;

public interface StudylogRepositoryCustom {

    List<Studylog> search(StudylogSearchCondition condition);

    Page<Studylog> search(StudylogSearchCondition condition, Pageable pageable);
}
