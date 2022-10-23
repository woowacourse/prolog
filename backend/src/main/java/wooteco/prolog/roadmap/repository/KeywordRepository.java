package wooteco.prolog.roadmap.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wooteco.prolog.roadmap.domain.Keyword;

public interface KeywordRepository extends JpaRepository<Keyword, Long> {

}