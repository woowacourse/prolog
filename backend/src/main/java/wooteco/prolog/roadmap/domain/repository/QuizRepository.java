package wooteco.prolog.roadmap.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import wooteco.prolog.roadmap.domain.Quiz;

import java.util.List;

public interface QuizRepository extends JpaRepository<Quiz, Long> {

    @Query("SELECT q FROM Quiz q"
        + " JOIN FETCH q.keyword k"
        + " WHERE q.keyword.id = :keywordId")
    List<Quiz> findFetchQuizByKeywordId(Long keywordId);
}
