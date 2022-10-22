package wooteco.prolog.roadmap.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import wooteco.prolog.roadmap.Keyword;

public interface KeywordRepository extends JpaRepository<Keyword, Long> {

    @Query("SELECT k FROM Keyword k join fetch k.children where k.id = :keywordId ")
    Keyword findFetchById(Long keywordId);
}
