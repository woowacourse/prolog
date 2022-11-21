package wooteco.prolog.roadmap.application;

import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wooteco.prolog.roadmap.application.dto.QuizRequest;
import wooteco.prolog.roadmap.application.dto.QuizResponse;
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
        final Quiz quiz = quizRepository.save(new Quiz(keyword, quizRequest.getQuestion()));
        return quiz.getId();
    }

    public QuizzesResponse findQuizzesByKeywordId(Long keywordId) {
        final List<Quiz> quizzes = quizRepository.findFetchQuizByKeywordId(keywordId);
        return QuizzesResponse.of(keywordId, quizzes);
    }

    @Transactional
    public void updateQuiz(Long quizId, QuizRequest quizRequest) {
        final Quiz quiz = quizRepository.findById(quizId).orElseThrow(QuizNotFoundException::new);
        quiz.update(quizRequest.getQuestion());
    }

    @Transactional
    public void deleteQuiz(Long quizId) {
        if (!quizRepository.existsById(quizId)) {
            throw new QuizNotFoundException();
        }
        quizRepository.deleteById(quizId);
    }

    public QuizResponse findById(Long quizId) {
        final Quiz quiz = quizRepository.findById(quizId).orElseThrow(QuizNotFoundException::new);
        return QuizResponse.of(quiz);
    }
}
