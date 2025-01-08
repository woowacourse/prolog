package wooteco.prolog.session.domain.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import wooteco.prolog.session.domain.Question;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    List<Question> findByMissionId(Long missionId);
}
