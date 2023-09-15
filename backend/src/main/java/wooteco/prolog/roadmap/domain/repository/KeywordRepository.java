package wooteco.prolog.roadmap.domain.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import wooteco.prolog.roadmap.domain.Keyword;

public interface KeywordRepository extends JpaRepository<Keyword, Long> {

    @Query("SELECT k FROM Keyword k "
        + "LEFT JOIN FETCH k.children c "
        + "LEFT JOIN FETCH k.parent p "
        + "LEFT JOIN FETCH c.children lc WHERE k.id = :keywordId ORDER BY k.seq")
    Keyword findFetchById(@Param("keywordId") Long keywordId);

    @Query("SELECT k FROM Keyword k "
        + "WHERE k.parent IS NULL")
    List<Keyword> findByParentIsNull();
}
