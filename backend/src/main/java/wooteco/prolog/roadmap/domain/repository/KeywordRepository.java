package wooteco.prolog.roadmap.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import wooteco.prolog.roadmap.domain.Keyword;
import wooteco.prolog.roadmap.domain.repository.dto.KeywordIdAndAnsweredQuizCount;
import wooteco.prolog.roadmap.domain.repository.dto.KeywordIdAndTotalQuizCount;

import java.util.List;

public interface KeywordRepository extends JpaRepository<Keyword, Long> {

    @Query("SELECT k.id AS keywordId, COUNT (q.id) as totalQuizCount " +
        "FROM Keyword k " +
        "JOIN Quiz q ON q.keyword.id = k.id " +
        "GROUP BY k.id")
    List<KeywordIdAndTotalQuizCount> findTotalQuizCount();

    @Query("SELECT k.id AS keywordId, COUNT (e.id) AS answeredQuizCount " +
        "FROM Keyword k " +
        "JOIN Quiz q ON k.id = q.keyword.id " +
        "JOIN EssayAnswer e ON e.quiz.id = q.id " +
        "WHERE e.member.id = :memberId " +
        "GROUP BY k.id ")
    List<KeywordIdAndAnsweredQuizCount> findAnsweredQuizCountByMemberId(@Param("memberId") Long memberId);

    Keyword findFetchByIdOrderBySeq(@Param("keywordId") Long keywordId);

    @Query("SELECT k FROM Keyword k "
        + "LEFT JOIN FETCH k.recommendedPosts "
        + "WHERE k.sessionId = :sessionId AND k.parent IS NULL")
    List<Keyword> findBySessionIdAndParentIsNull(@Param("sessionId") Long sessionId);

    @Query("SELECT k FROM Keyword k "
        + "LEFT JOIN FETCH k.recommendedPosts "
        + "WHERE k.parent IS NULL")
    List<Keyword> newFindByParentIsNull();

    @Query("SELECT k FROM Keyword k " +
        "JOIN Session s ON s.id = k.sessionId " +
        "WHERE s.curriculumId = :curriculumId ")
    List<Keyword> findAllByCurriculumId(Long curriculumId);
}
