package wooteco.prolog.roadmap.domain.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import wooteco.prolog.roadmap.domain.EssayAnswer;

public interface EssayAnswerRepository extends JpaRepository<EssayAnswer, Long>,
    JpaSpecificationExecutor<EssayAnswer> {

    boolean existsByQuizIdAndMemberId(Long quizId, Long memberId);

    Optional<EssayAnswer> findByIdAndMemberId(Long id, Long memberId);

    List<EssayAnswer> findByQuizIdOrderByIdDesc(Long quizId);

    @Query("SELECT e FROM EssayAnswer e " +
        "LEFT JOIN FETCH e.quiz q " +
        "WHERE e.member.id = :memberId")
    Set<EssayAnswer> findAllByMemberId(@Param("memberId") Long memberId);
}
