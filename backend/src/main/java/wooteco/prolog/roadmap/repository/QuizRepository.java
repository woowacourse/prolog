package wooteco.prolog.roadmap.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wooteco.prolog.roadmap.Keyword;

public interface QuizRepository extends JpaRepository<Keyword, Long> {

}
