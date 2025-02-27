package wooteco.prolog.session.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wooteco.prolog.session.domain.Answer;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, Long> {

    List<Answer> findByStudylogId(Long studylogId);

    List<Answer> findByStudylogIdIn(List<Long> studylogIds);
}
