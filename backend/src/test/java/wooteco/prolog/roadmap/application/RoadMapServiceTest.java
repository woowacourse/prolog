package wooteco.prolog.roadmap.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.member.domain.Role;
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

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RoadMapServiceTest {

    @Mock
    private CurriculumRepository curriculumRepository;
    @Mock
    private SessionRepository sessionRepository;
    @Mock
    private KeywordRepository keywordRepository;
    @Mock
    private QuizRepository quizRepository;
    @Mock
    private EssayAnswerRepository essayAnswerRepository;
    @InjectMocks
    private RoadMapService roadMapService;

    @Test
    @DisplayName("curriculumId가 주어지면 해당 커리큘럼의 키워드들을 전부 조회할 수 있다.")
    void findAllKeywords() {
        //given
        final Curriculum curriculum = new Curriculum(1L, "커리큘럼1");
        final Session session = new Session(1L, curriculum.getId(), "세션1");
        final List<Session> sessions = Arrays.asList(session);
        final Keyword keyword = new Keyword(1L, "자바1", "자바 설명1", 1, 5, session.getId(),
            null, Collections.emptySet());

        when(curriculumRepository.findById(anyLong()))
            .thenReturn(Optional.of(curriculum));

        when(sessionRepository.findAllByCurriculumId(anyLong()))
            .thenReturn(sessions);

        when(keywordRepository.findBySessionIdIn(any()))
            .thenReturn(Arrays.asList(keyword));

        final KeywordsResponse expected = KeywordsResponse.createResponseWithChildren(Arrays.asList(keyword));

        //when
        final KeywordsResponse actual =
            roadMapService.findAllKeywords(curriculum.getId());

        //then
        assertThat(actual)
            .usingRecursiveComparison()
            .isEqualTo(expected);
    }

    @Test
    @DisplayName("curriculumId가 주어지면 해당 커리큘럼의 키워드들을 학습 진도와 함께 전부 조회할 수 있다.")
    void findAllKeywordsWithProgress() {
        //given
        final Curriculum curriculum = new Curriculum(1L, "커리큘럼1");
        final Session session = new Session(1L, curriculum.getId(), "세션1");
        final List<Session> sessions = Arrays.asList(session);
        final Keyword keyword = new Keyword(1L, "자바1", "자바 설명1", 1, 5, session.getId(),
            null, Collections.emptySet());
        final Quiz quiz = new Quiz(1L, keyword, "자바8을 왜 쓰나요?");
        final Member member = new Member(1L, "연어", "참치", Role.CREW, 1L, "image");
        final EssayAnswer essayAnswer = new EssayAnswer(quiz, "쓰라고 해서요ㅠㅠ", member);

        when(curriculumRepository.findById(anyLong()))
            .thenReturn(Optional.of(curriculum));

        when(sessionRepository.findAllByCurriculumId(anyLong()))
            .thenReturn(sessions);

        when(keywordRepository.findBySessionIdIn(any()))
            .thenReturn(Arrays.asList(keyword));

        when(essayAnswerRepository.findAllByMemberId(1L))
            .thenReturn(new HashSet<>(Arrays.asList(essayAnswer)));

        when(quizRepository.findAll())
            .thenReturn(Arrays.asList(quiz));

        //when
        final KeywordsResponse actual =
            roadMapService.findAllKeywordsWithProgress(curriculum.getId(), 1L);

        //then
        final List<KeywordResponse> responses = actual.getData();
        assertSoftly(soft -> {
            assertThat(responses).hasSize(1);
            assertThat(responses.get(0).getDoneQuizCount()).isOne();
            assertThat(responses.get(0).getTotalQuizCount()).isOne();
        });
    }
}
