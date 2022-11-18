package wooteco.prolog.roadmap.application;

import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wooteco.prolog.roadmap.application.dto.QuizRequest;
import wooteco.prolog.roadmap.application.dto.QuizzesResponse;
import wooteco.prolog.roadmap.domain.Keyword;
import wooteco.prolog.roadmap.domain.Quiz;
import wooteco.prolog.roadmap.domain.repository.KeywordRepository;
import wooteco.prolog.roadmap.domain.repository.QuizRepository;
import wooteco.prolog.roadmap.exception.KeywordOrderException;
import wooteco.prolog.roadmap.exception.QuizNotFoundException;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class QuizService {

    private final KeywordRepository keywordRepository;
    private final QuizRepository quizRepository;

    @Transactional
    public Long createQuiz(Long keywordId, QuizRequest quizRequest) {
        final Keyword keyword = keywordRepository.findById(keywordId)
            .orElseThrow(KeywordOrderException::new);

        return quizRepository.save(new Quiz(keyword, quizRequest.getQuestion())).getId();
    }

    public QuizzesResponse findQuizzes(Long keywordId) {
        final List<Quiz> quizzes = quizRepository.findQuizByKeywordId(keywordId);
        return QuizzesResponse.of(keywordId, quizzes);
    }

    @Transactional
    public void deleteQuiz(Long quizId) {
        if (!quizRepository.existsById(quizId)) {
            throw new QuizNotFoundException();
        }
        quizRepository.deleteById(quizId);
    }
}
