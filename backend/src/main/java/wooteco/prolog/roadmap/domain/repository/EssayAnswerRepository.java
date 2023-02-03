package wooteco.prolog.roadmap.domain.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import wooteco.prolog.roadmap.domain.EssayAnswer;

public interface EssayAnswerRepository extends JpaRepository<EssayAnswer, Long> {

    Optional<EssayAnswer> findByIdAndMemberId(Long id, Long memberId);

    List<EssayAnswer> findByQuizId(Long quizId);
}
