package wooteco.prolog.roadmap.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.junit.jupiter.api.Assertions.assertAll;

import jakarta.persistence.EntityManager;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.member.domain.Role;
import wooteco.prolog.member.domain.repository.MemberRepository;
import wooteco.prolog.roadmap.domain.Curriculum;
import wooteco.prolog.roadmap.domain.EssayAnswer;
import wooteco.prolog.roadmap.domain.Keyword;
import wooteco.prolog.roadmap.domain.Quiz;
import wooteco.prolog.roadmap.domain.repository.CurriculumRepository;
import wooteco.prolog.roadmap.domain.repository.EssayAnswerRepository;
import wooteco.prolog.roadmap.domain.repository.KeywordRepository;
import wooteco.prolog.roadmap.domain.repository.QuizRepository;
import wooteco.prolog.roadmap.domain.repository.dto.KeywordIdAndAnsweredQuizCount;
import wooteco.prolog.roadmap.domain.repository.dto.KeywordIdAndTotalQuizCount;
import wooteco.prolog.session.domain.Session;
import wooteco.prolog.session.domain.repository.SessionRepository;
import wooteco.support.utils.RepositoryTest;

@RepositoryTest
class KeywordRepositoryTest {

    @Autowired
    private KeywordRepository keywordRepository;
    @Autowired
    private SessionRepository sessionRepository;
    @Autowired
    private EssayAnswerRepository essayAnswerRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private EntityManager em;
    @Autowired
    private CurriculumRepository curriculumRepository;
    @Autowired
    private QuizRepository quizRepository;

    @Test
    void 부모_키워드와_1뎁스까지의_자식_키워드를_함께_조회할_수_있다() {
        // given
        Long sessionId = createSession();

        Keyword keywordParent = createKeywordParent(
            Keyword.createKeyword("자바", "자바에 대한 설명", 1, 1, sessionId, null));
        createKeywordChildren(
            Keyword.createKeyword("List", "List에 대한 설명", 1, 1, sessionId, keywordParent));
        createKeywordChildren(
            Keyword.createKeyword("Set", "Set에 대한 설명", 2, 1, sessionId, keywordParent));
        Long keywordParentId = keywordParent.getId();
        em.clear();

        // when
        Keyword extract = keywordRepository.findFetchByIdOrderBySeq(keywordParentId);

        // then
        assertAll(
            () -> assertThat(extract.getId()).isNotNull(),
            () -> assertThat(extract.getChildren()).hasSize(2));
    }

    @Test
    void 부모_키워드와_2뎁스까지의_자식_키워드를_함께_조회할_수_있다() {
        // given
        Long sessionId = createSession();

        Keyword parent = createKeywordParent(
            Keyword.createKeyword("자바", "자바에 대한 설명", 1, 1, sessionId, null));

        Keyword children1 = createKeywordChildren(
            Keyword.createKeyword("List", "List에 대한 설명", 1, 1, sessionId, parent));
        Keyword children2 = createKeywordChildren(
            Keyword.createKeyword("Set", "Set에 대한 설명", 2, 1, sessionId, parent));

        createKeywordChildren(
            Keyword.createKeyword("List.of()", "List.of()에 대한 설명", 1, 1, sessionId, children1));
        createKeywordChildren(
            Keyword.createKeyword("Set.of()", "Set.of()에 대한 설명", 1, 1, sessionId, children2));

        Long keywordParentId = parent.getId();
        em.clear();

        // when
        Keyword extract = keywordRepository.findFetchByIdOrderBySeq(keywordParentId);

        // then
        assertAll(
            () -> assertThat(extract.getId()).isNotNull(),
            () -> assertThat(extract.getChildren()).hasSize(2),
            () -> assertThat(extract.getChildren().iterator().next()
                .getChildren()).hasSize(1)
        );
    }

    @Test
    void 최상위_키워드만_조회한다() {
        // given
        Long sessionId = createSession();

        Keyword keywordParent = createKeywordParent(
            Keyword.createKeyword("자바", "자바에 대한 설명", 1, 1, sessionId, null));
        createKeywordChildren(
            Keyword.createKeyword("List", "List에 대한 설명", 1, 1, sessionId, keywordParent));
        createKeywordChildren(
            Keyword.createKeyword("Set", "Set에 대한 설명", 2, 1, sessionId, keywordParent));
        Long keywordParentId = keywordParent.getId();
        em.clear();

        // when
        final List<Keyword> extractParentKeyword = keywordRepository.findBySessionIdAndParentIsNull(
            sessionId);

        // then
        assertThat(extractParentKeyword).hasSize(1);
    }

    @Test
    @DisplayName("newFindParentIsNull() : 모든 상위 키워드들을 조회할 수 있다.")
    void newFindParentIsNull() {
        //given
        final Curriculum curriculum = curriculumRepository.save(new Curriculum("커리큘럼1"));

        final Session session1 = sessionRepository.save(new Session(curriculum.getId(), "세션1"));
        final Session session2 = sessionRepository.save(new Session(curriculum.getId(), "세션2"));
        final Session session3 = sessionRepository.save(new Session(curriculum.getId(), "세션3"));
        final Session session4 = sessionRepository.save(new Session(curriculum.getId(), "세션4"));
        final Session session5 = sessionRepository.save(new Session(curriculum.getId(), "세션5"));

        final Keyword keyword1 = keywordRepository.save(
            Keyword.createKeyword("자바1", "자바 설명1", 1, 5, session1.getId(), null));
        final Keyword keyword2 = keywordRepository.save(
            Keyword.createKeyword("자바2", "자바 설명2", 2, 5, session1.getId(), keyword1));
        final Keyword keyword3 = keywordRepository.save(
            Keyword.createKeyword("자바3", "자바 설명3", 3, 5, session1.getId(), null));
        final Keyword keyword4 = keywordRepository.save(
            Keyword.createKeyword("자바4", "자바 설명4", 4, 5, session1.getId(), keyword3));
        final Keyword keyword5 = keywordRepository.save(
            Keyword.createKeyword("자바5", "자바 설명5", 5, 5, session2.getId(), null));
        final Keyword keyword6 = keywordRepository.save(
            Keyword.createKeyword("자바6", "자바 설명6", 6, 5, session2.getId(), keyword1));
        final Keyword keyword7 = keywordRepository.save(
            Keyword.createKeyword("자바7", "자바 설명7", 7, 5, session2.getId(), null));
        final Keyword keyword8 = keywordRepository.save(
            Keyword.createKeyword("자바8", "자바 설명8", 8, 5, session3.getId(), keyword2));
        final Keyword keyword9 = keywordRepository.save(
            Keyword.createKeyword("자바9", "자바 설명9", 9, 5, session4.getId(), keyword2));
        final Keyword keyword10 = keywordRepository.save(
            Keyword.createKeyword("자바10", "자바 설명10", 10, 5, session5.getId(), null));

        //when
        final List<Keyword> keywords = keywordRepository.newFindByParentIsNull();

        //then
        assertThat(keywords)
            .usingRecursiveComparison()
            .ignoringFields("id", "parent.id")
            .isEqualTo(Arrays.asList(keyword1, keyword3, keyword5, keyword7, keyword10));
    }

    @Test
    @DisplayName("각 키워드별 퀴즈 개수를 조회할 수 있다")
    void findTotalQuizCount() {
        //given
        final Long sessionId = createSession();

        final Keyword parent = createKeywordParent(
            Keyword.createKeyword("자바", "자바에 대한 설명", 1, 1, sessionId, null));
        final Keyword child1 = createKeywordChildren(
            Keyword.createKeyword("List", "List에 대한 설명", 1, 1, sessionId, parent));
        createKeywordChildren(
            Keyword.createKeyword("Set", "Set에 대한 설명", 2, 1, sessionId, parent));

        quizRepository.save(new Quiz(parent, "자바란 무엇일까요?"));
        quizRepository.save(new Quiz(parent, "자바를 왜 쓰죠?"));
        quizRepository.save(new Quiz(child1, "리스트보단 배열이 낫지 않나요?"));

        //when
        final List<KeywordIdAndTotalQuizCount> totalQuizCounts = keywordRepository.findTotalQuizCount();

        //then
        assertSoftly(softAssertions -> {
            softAssertions.assertThat(totalQuizCounts)
                .map(KeywordIdAndTotalQuizCount::getKeywordId)
                .containsExactlyInAnyOrder(parent.getId(), child1.getId());

            softAssertions.assertThat(totalQuizCounts)
                .map(KeywordIdAndTotalQuizCount::getTotalQuizCount)
                .containsExactlyInAnyOrder(1, 2);
        });
    }

    @Test
    @DisplayName("회원의 id로 각 키워드별 완료한 답변 개수를 조회할 수 있다")
    void findDoneQuizCountByMemberId() {
        //given
        final Long sessionId = createSession();

        final Keyword parent = createKeywordParent(
            Keyword.createKeyword("자바", "자바에 대한 설명", 1, 1, sessionId, null));
        final Keyword child1 = createKeywordChildren(
            Keyword.createKeyword("List", "List에 대한 설명", 1, 1, sessionId, parent));

        final Quiz quiz1 = quizRepository.save(new Quiz(parent, "자바란 무엇일까요?"));
        final Quiz quiz2 = quizRepository.save(new Quiz(parent, "코틀린은 뭘까요?"));
        quizRepository.save(new Quiz(child1, "리스트보단 배열이 낫지 않나요?"));

        final Member member = memberRepository.save(new Member("username1", "nickname1", Role.CREW, 1L, "imageUrl1"));
        essayAnswerRepository.save(new EssayAnswer(quiz1, "쓰라고 해서요ㅠ", member));
        essayAnswerRepository.save(new EssayAnswer(quiz2, "쓰라고 해서요ㅠ", member));

        //when
        final List<KeywordIdAndAnsweredQuizCount> doneQuizCounts = keywordRepository.findAnsweredQuizCountByMemberId(
            member.getId());

        //then
        assertSoftly(softAssertions -> {
            softAssertions.assertThat(doneQuizCounts)
                .map(KeywordIdAndAnsweredQuizCount::getKeywordId)
                .containsExactly(parent.getId());
            softAssertions.assertThat(doneQuizCounts)
                .map(KeywordIdAndAnsweredQuizCount::getAnsweredQuizCount)
                .containsExactly(2);
        });
    }

    @Test
    @DisplayName("커리큘럼 ID에 해당하는 모든 키워드들을 조회할 수 있다")
    void findAllByCurriculumId() {
        //given
        final Long session1 = createSessionWithCurriculumId(1L);
        final Long session2 = createSessionWithCurriculumId(1L);

        final Keyword parent1 = createKeywordParent(
            Keyword.createKeyword("자바", "자바에 대한 설명", 1, 1, session1, null));
        createKeywordChildren(
            Keyword.createKeyword("List", "List에 대한 설명", 1, 1, session1, parent1));
        createKeywordChildren(
            Keyword.createKeyword("List", "List에 대한 설명", 1, 1, session1, parent1));

        createKeywordParent(
            Keyword.createKeyword("자바", "자바에 대한 설명", 1, 1, session2, null));

        //when
        final List<Keyword> keywords = keywordRepository.findAllByCurriculumId(1L);

        //then
        assertThat(keywords).hasSize(4);
    }

    private Long createSession() {
        Session session = new Session("테스트 세션");
        sessionRepository.save(session);
        return session.getId();
    }

    private Long createSessionWithCurriculumId(final Long curriculumId) {
        Session session = new Session(curriculumId, "테스트 세션");
        sessionRepository.save(session);
        return session.getId();
    }

    private Keyword createKeywordParent(final Keyword keyword) {
        return keywordRepository.save(keyword);
    }

    private Keyword createKeywordChildren(final Keyword keyword) {
        return keywordRepository.save(keyword);
    }
}
