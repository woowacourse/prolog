package wooteco.prolog.roadmap.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wooteco.prolog.roadmap.domain.Keyword;

public interface QuizRepository extends JpaRepository<Keyword, Long> {

}
