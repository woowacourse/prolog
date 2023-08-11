package wooteco.prolog.roadmap.application;

import static wooteco.prolog.common.exception.BadRequestCode.ROADMAP_KEYWORD_ORDER_EXCEPTION;
import static wooteco.prolog.common.exception.BadRequestCode.ROADMAP_QUIZ_NOT_FOUND_EXCEPTION;

import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wooteco.prolog.common.exception.BadRequestException;
import wooteco.prolog.roadmap.application.dto.QuizRequest;
import wooteco.prolog.roadmap.application.dto.QuizResponse;
import wooteco.prolog.roadmap.application.dto.QuizzesResponse;
import wooteco.prolog.roadmap.domain.Keyword;
import wooteco.prolog.roadmap.domain.Quiz;
import wooteco.prolog.roadmap.domain.repository.EssayAnswerRepository;
import wooteco.prolog.roadmap.domain.repository.KeywordRepository;
import wooteco.prolog.roadmap.domain.repository.QuizRepository;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class QuizService {

    private final KeywordRepository keywordRepository;
    private final QuizRepository quizRepository;
    private final EssayAnswerRepository essayAnswerRepository;

    @Transactional
    public Long createQuiz(Long keywordId, QuizRequest quizRequest) {
        final Keyword keyword = keywordRepository.findById(keywordId)
            .orElseThrow(() -> new BadRequestException(ROADMAP_KEYWORD_ORDER_EXCEPTION));
        final Quiz quiz = quizRepository.save(new Quiz(keyword, quizRequest.getQuestion()));
        return quiz.getId();
    }

    public QuizzesResponse findQuizzesByKeywordId(Long keywordId, Long memberId) {
        final List<Quiz> quizzes = quizRepository.findFetchQuizByKeywordId(keywordId);
        final List<QuizResponse> quizResponses = quizzes.stream()
            .map(quiz -> QuizResponse.of(quiz, isLearning(memberId, quiz.getId())))
            .collect(Collectors.toList());
        return QuizzesResponse.of(keywordId, quizResponses);
    }

    private boolean isLearning(Long memberId, Long quizId) {
        if (memberId == null) {
            return false;
        }
        return essayAnswerRepository.existsByQuizIdAndMemberId(quizId,
            memberId);
    }

    @Transactional
    public void updateQuiz(Long quizId, QuizRequest quizRequest) {
        final Quiz quiz = quizRepository.findById(quizId)
            .orElseThrow(() -> new BadRequestException(ROADMAP_QUIZ_NOT_FOUND_EXCEPTION));
        quiz.update(quizRequest.getQuestion());
    }

    @Transactional
    public void deleteQuiz(Long quizId) {
        if (!quizRepository.existsById(quizId)) {
            throw new BadRequestException(ROADMAP_QUIZ_NOT_FOUND_EXCEPTION);
        }
        quizRepository.deleteById(quizId);
    }

    public QuizResponse findById(Long quizId, Long memberId) {
        final Quiz quiz = quizRepository.findById(quizId)
            .orElseThrow(() -> new BadRequestException(ROADMAP_QUIZ_NOT_FOUND_EXCEPTION));
        return QuizResponse.of(quiz, isLearning(memberId, quizId));
    }
}
