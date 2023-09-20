package wooteco.prolog.roadmap.domain.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import wooteco.prolog.roadmap.domain.Keyword;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.springframework.data.jpa.repository.EntityGraph.EntityGraphType.FETCH;

public interface KeywordRepository extends JpaRepository<Keyword, Long> {

    @EntityGraph(attributePaths = "recommendedPosts", type = FETCH)
    Optional<Keyword> findById(final Long id);

    @EntityGraph(attributePaths = "recommendedPosts", type = FETCH)
    List<Keyword> findAll();

    @Query("SELECT k FROM Keyword k "
        + "LEFT JOIN FETCH k.children c "
        + "LEFT JOIN FETCH k.recommendedPosts "
        + "LEFT JOIN FETCH k.parent p "
        + "LEFT JOIN FETCH p.recommendedPosts "
        + "LEFT JOIN FETCH c.recommendedPosts "
        + "LEFT JOIN FETCH c.children lc "
        + "LEFT JOIN FETCH lc.recommendedPosts "
        + "LEFT JOIN FETCH lc.children "
        + "WHERE k.id = :keywordId ORDER BY k.seq")
    Keyword findFetchByIdOrderBySeq(@Param("keywordId") Long keywordId);

    @Query("SELECT k FROM Keyword k "
        + "LEFT JOIN FETCH k.recommendedPosts "
        + "WHERE k.sessionId = :sessionId AND k.parent IS NULL")
    List<Keyword> findBySessionIdAndParentIsNull(@Param("sessionId") Long sessionId);

    @Query("SELECT k FROM Keyword k "
        + "LEFT JOIN FETCH k.recommendedPosts "
        + "WHERE k.parent IS NULL")
    List<Keyword> newFindParentIsNull();

    List<Keyword> findBySessionIdIn(final Set<Long> sessionIds);
}
