package wooteco.prolog.roadmap.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wooteco.prolog.common.exception.BadRequestException;
import wooteco.prolog.roadmap.application.dto.KeywordResponse;
import wooteco.prolog.roadmap.application.dto.KeywordsResponse;
import wooteco.prolog.roadmap.domain.Curriculum;
import wooteco.prolog.roadmap.domain.EssayAnswer;
import wooteco.prolog.roadmap.domain.Keyword;
import wooteco.prolog.roadmap.domain.Quiz;
import wooteco.prolog.roadmap.domain.repository.CurriculumRepository;
import wooteco.prolog.roadmap.domain.repository.EssayAnswerRepository;
import wooteco.prolog.roadmap.domain.repository.KeywordRepository;
import wooteco.prolog.roadmap.domain.repository.QuizRepository;
import wooteco.prolog.session.domain.Session;
import wooteco.prolog.session.domain.repository.SessionRepository;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static wooteco.prolog.common.exception.BadRequestCode.CURRICULUM_NOT_FOUND_EXCEPTION;

@RequiredArgsConstructor
@Transactional
@Service
public class RoadMapService {

    private final CurriculumRepository curriculumRepository;
    private final SessionRepository sessionRepository;
    private final KeywordRepository keywordRepository;
    private final QuizRepository quizRepository;
    private final EssayAnswerRepository essayAnswerRepository;

    @Transactional(readOnly = true)
    public KeywordsResponse findAllKeywordsWithProgress(final Long curriculumId, final Long memberId) {
        final Curriculum curriculum = curriculumRepository.findById(curriculumId)
            .orElseThrow(() -> new BadRequestException(CURRICULUM_NOT_FOUND_EXCEPTION));

        final Set<Long> sessionIds = sessionRepository.findAllByCurriculumId(curriculum.getId())
            .stream()
            .map(Session::getId)
            .collect(Collectors.toSet());

        final List<Keyword> keywords = keywordRepository.findBySessionIdIn(sessionIds);

        final Set<Quiz> doneQuizzes = essayAnswerRepository.findAllByMemberId(memberId).stream()
            .map(EssayAnswer::getQuiz)
            .collect(Collectors.toSet());

        final Map<Keyword, Set<Quiz>> quizzesPerKeyword = quizRepository.findAll().stream()
            .collect(Collectors.groupingBy(Quiz::getKeyword, Collectors.toSet()));

        return createWithProgress(keywords, quizzesPerKeyword, doneQuizzes);
    }

    private KeywordsResponse createWithProgress(final List<Keyword> keywords,
                                                final Map<Keyword, Set<Quiz>> quizzesPerKeyword,
                                                final Set<Quiz> doneQuizzes) {
        final List<KeywordResponse> keywordResponses = keywords.stream()
            .filter(Keyword::isRoot)
            .map(keyword -> createWithProgress(keyword, quizzesPerKeyword, doneQuizzes))
            .collect(Collectors.toList());

        return new KeywordsResponse(keywordResponses);
    }

    private KeywordResponse createWithProgress(final Keyword keyword,
                                               final Map<Keyword, Set<Quiz>> quizzesPerKeyword,
                                               final Set<Quiz> doneQuizzes) {
        final int totalQuizCount = quizzesPerKeyword.get(keyword).size();
        final Set<Quiz> quizzes = quizzesPerKeyword.get(keyword);
        quizzes.retainAll(doneQuizzes);
        final int doneQuizCount = quizzes.size();

        return new KeywordResponse(
            keyword.getId(),
            keyword.getName(),
            keyword.getDescription(),
            keyword.getSeq(),
            keyword.getImportance(),
            totalQuizCount,
            doneQuizCount,
            keyword.getParentIdOrNull(),
            createChildrenWithProgress(keyword.getChildren(), quizzesPerKeyword, doneQuizzes)
        );
    }

    private Set<KeywordResponse> createChildrenWithProgress(final Set<Keyword> children,
                                                            final Map<Keyword, Set<Quiz>> quizzesPerKeyword,
                                                            final Set<Quiz> userAnswers) {
        return children.stream()
            .map(child -> createWithProgress(child, quizzesPerKeyword, userAnswers))
            .collect(Collectors.toSet());
    }
}
