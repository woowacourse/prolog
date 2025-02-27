package wooteco.prolog.session.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wooteco.prolog.session.domain.AnswerFeedback;

public interface AnswerFeedbackRepository extends JpaRepository<AnswerFeedback, Long> {

}
