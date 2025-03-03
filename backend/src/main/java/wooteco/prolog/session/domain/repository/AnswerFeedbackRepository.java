package wooteco.prolog.session.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import wooteco.prolog.session.domain.AnswerFeedback;

import java.util.List;

public interface AnswerFeedbackRepository extends JpaRepository<AnswerFeedback, Long> {

    @Query("""
            SELECT af
            FROM AnswerFeedback af
            JOIN (
                SELECT MAX(id) AS id
                FROM AnswerFeedback
                WHERE memberId = :memberId AND question.id IN (:questionIds) AND visible = TRUE
                GROUP BY question.id
            ) sub
            ON af.id = sub.id
            """)
    List<AnswerFeedback> findRecentByMemberIdAndQuestionIdsAndVisible(Long memberId, List<Long> questionIds);
}
