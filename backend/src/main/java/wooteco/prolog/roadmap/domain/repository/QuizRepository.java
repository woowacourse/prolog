package wooteco.prolog.roadmap.domain.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import wooteco.prolog.roadmap.domain.Quiz;

public interface QuizRepository extends JpaRepository<Quiz, Long> {

    @Query("SELECT q FROM Quiz q"
        + " JOIN FETCH q.keyword k"
        + " WHERE q.keyword.id = :keywordId")
    List<Quiz> findQuizByKeywordId(Long keywordId);
}
