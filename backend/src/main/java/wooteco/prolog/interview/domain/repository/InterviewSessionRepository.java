package wooteco.prolog.interview.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wooteco.prolog.interview.domain.InterviewSession;

public interface InterviewSessionRepository extends JpaRepository<InterviewSession, Long> {

}
