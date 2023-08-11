package wooteco.prolog.roadmap.domain.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import wooteco.prolog.roadmap.domain.EssayAnswer;

public interface EssayAnswerRepository extends JpaRepository<EssayAnswer, Long> {

    @Query("select ea from EssayAnswer ea where ea.quiz.id = :quizId and ea.member.id = :memberId ")
    boolean existsByQuizIdAndMemberId(Long quizId, Long memberId);

    Optional<EssayAnswer> findByIdAndMemberId(Long id, Long memberId);

    List<EssayAnswer> findByQuizIdOrderByIdDesc(Long quizId);
}
