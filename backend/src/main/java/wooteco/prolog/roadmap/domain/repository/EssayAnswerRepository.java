package wooteco.prolog.roadmap.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;
import wooteco.prolog.roadmap.domain.EssayAnswer;

import java.util.List;
import java.util.Optional;

public interface EssayAnswerRepository extends JpaRepository<EssayAnswer, Long>,
    JpaSpecificationExecutor<EssayAnswer> {

    @Query("select ea from EssayAnswer ea where ea.quiz.id = :quizId and ea.member.id = :memberId")
    boolean existsByQuizIdAndMemberId(Long quizId, Long memberId);

    Optional<EssayAnswer> findByIdAndMemberId(Long id, Long memberId);

    List<EssayAnswer> findByQuizIdOrderByIdDesc(Long quizId);

    @Query("SELECT e FROM EssayAnswer e " +
        "LEFT JOIN FETCH e.quiz " +
        "WHERE e.member.id = :memberId")
    List<EssayAnswer> findAllByMemberId(@Param("memberId") Long memberId);
}
