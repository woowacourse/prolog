package wooteco.prolog.session.domain.repository;

import java.util.Arrays;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import wooteco.prolog.session.domain.Answer;

public interface AnswerRepository extends JpaRepository<Answer, Long> {

    List<Answer> findByStudylogId(Long studylogId);

    List<Answer> findByStudylogIdIn(List<Long> studylogIds);
}
