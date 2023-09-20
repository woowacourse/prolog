package wooteco.prolog.admin.roadmap.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wooteco.prolog.common.exception.BadRequestException;
import wooteco.prolog.admin.roadmap.application.dto.KeywordResponse;
import wooteco.prolog.admin.roadmap.application.dto.KeywordsResponse;
import wooteco.prolog.admin.roadmap.application.dto.RecommendedPostResponse;
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

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;
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

        final List<Keyword> keywordsInCurriculum = getKeywordsInCurriculum(curriculum);

        final Map<Keyword, Set<Quiz>> quizzesInKeywords = quizRepository.findAll().stream()
            .collect(groupingBy(Quiz::getKeyword, toSet()));

        return createResponsesWithProgress(keywordsInCurriculum, quizzesInKeywords, getDoneQuizzes(memberId));
    }

    private Set<Quiz> getDoneQuizzes(final Long memberId) {
        return essayAnswerRepository.findAllByMemberId(memberId).stream()
            .map(EssayAnswer::getQuiz)
            .collect(toSet());
    }

    private List<Keyword> getKeywordsInCurriculum(final Curriculum curriculum) {
        final Set<Long> sessionIds = sessionRepository.findAllByCurriculumId(curriculum.getId())
            .stream()
            .map(Session::getId)
            .collect(toSet());

        return keywordRepository.findBySessionIdIn(sessionIds);
    }

    private KeywordsResponse createResponsesWithProgress(final List<Keyword> keywords,
                                                         final Map<Keyword, Set<Quiz>> quizzesPerKeyword,
                                                         final Set<Quiz> doneQuizzes) {
        final List<KeywordResponse> keywordResponses = keywords.stream()
            .filter(Keyword::isRoot)
            .map(keyword -> createResponseWithProgress(keyword, quizzesPerKeyword, doneQuizzes))
            .collect(toList());

        return new KeywordsResponse(keywordResponses);
    }

    private KeywordResponse createResponseWithProgress(final Keyword keyword,
                                                       final Map<Keyword, Set<Quiz>> quizzesPerKeyword,
                                                       final Set<Quiz> doneQuizzes) {
        final int totalQuizCount = quizzesPerKeyword.get(keyword).size();
        final int doneQuizCount = getDoneQuizCount(quizzesPerKeyword.get(keyword), doneQuizzes);

        final List<RecommendedPostResponse> recommendedPostResponses = keyword.getRecommendedPosts().stream()
            .map(RecommendedPostResponse::from)
            .collect(toList());

        return new KeywordResponse(
            keyword.getId(),
            keyword.getName(),
            keyword.getDescription(),
            keyword.getSeq(),
            keyword.getImportance(),
            totalQuizCount,
            doneQuizCount,
            keyword.getParentIdOrNull(),
            recommendedPostResponses,
            createChildrenWithProgress(keyword.getChildren(), quizzesPerKeyword, doneQuizzes)
        );
    }

    private int getDoneQuizCount(final Set<Quiz> quizzes, final Set<Quiz> doneQuizzes) {
        quizzes.retainAll(doneQuizzes);
        return quizzes.size();
    }

    private Set<KeywordResponse> createChildrenWithProgress(final Set<Keyword> children,
                                                            final Map<Keyword, Set<Quiz>> quizzesPerKeyword,
                                                            final Set<Quiz> userAnswers) {
        return children.stream()
            .map(child -> createResponseWithProgress(child, quizzesPerKeyword, userAnswers))
            .collect(toSet());
    }
}
