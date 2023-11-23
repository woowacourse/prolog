package wooteco.prolog.roadmap.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import wooteco.prolog.roadmap.application.dto.CurriculumQuizResponse;
import wooteco.prolog.roadmap.domain.Quiz;

import java.util.List;

public interface QuizRepository extends JpaRepository<Quiz, Long> {

    @Query("SELECT q FROM Quiz q"
        + " JOIN FETCH q.keyword k"
        + " WHERE q.keyword.id = :keywordId")
    List<Quiz> findFetchQuizByKeywordId(Long keywordId);

    @Query(nativeQuery = true,
        value = "SELECT q.id, q.question " +
            "FROM curriculum c " +
                "JOIN session s ON c.id = :curriculumId AND s.curriculum_id = c.id " +
                "JOIN keyword k ON k.session_id = s.id " +
                "JOIN quiz q ON q.keyword_id = k.id")
    List<CurriculumQuizResponse> findQuizzesByCurriculum(@Param("curriculumId") Long curriculumId);
}
