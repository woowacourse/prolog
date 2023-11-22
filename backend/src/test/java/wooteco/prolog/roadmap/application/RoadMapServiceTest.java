package wooteco.prolog.roadmap.application;

import org.assertj.core.api.AbstractListAssert;
import org.assertj.core.api.ObjectAssert;
import org.assertj.core.api.ProxyableListAssert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import wooteco.prolog.common.DataInitializer;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.member.domain.Role;
import wooteco.prolog.member.domain.repository.MemberRepository;
import wooteco.prolog.roadmap.application.dto.KeywordResponse;
import wooteco.prolog.roadmap.application.dto.KeywordsResponse;
import wooteco.prolog.roadmap.domain.EssayAnswer;
import wooteco.prolog.roadmap.domain.Keyword;
import wooteco.prolog.roadmap.domain.Quiz;
import wooteco.prolog.roadmap.domain.repository.EssayAnswerRepository;
import wooteco.prolog.roadmap.domain.repository.KeywordRepository;
import wooteco.prolog.roadmap.domain.repository.QuizRepository;
import wooteco.prolog.session.domain.Session;
import wooteco.prolog.session.domain.repository.SessionRepository;

import java.util.List;
import java.util.function.Consumer;

import static java.util.Collections.emptySet;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@SpringBootTest
class RoadMapServiceTest {

    @Autowired
    private SessionRepository sessionRepository;
    @Autowired
    private KeywordRepository keywordRepository;
    @Autowired
    private QuizRepository quizRepository;
    @Autowired
    private EssayAnswerRepository essayAnswerRepository;
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private RoadMapService roadMapService;

    @Autowired
    private DataInitializer dataInitializer;

    @AfterEach
    void truncate() {
        dataInitializer.execute();
    }

    @Test
    @DisplayName("커리큘럼 ID에 해당하는 모든 키워드를 진도율과 함께 조회할 수 있다 - 비회원")
    void findAllKeywordsWithProgress() {
        //given
        final Long curriculumId = 1L;
        final Long session1 = createSession(curriculumId);
        final Long session2 = createSession(curriculumId);

        final Keyword root1 = createParentKeyword("스트림", "스트림 API 설명", session1);
        final Keyword root2 = createParentKeyword("컬렉션", "컬렉션 API 설명", session2);

        final Keyword oneDepthChild = createChildKeyword(root1, "map()", "스트림 api의 map");
        final Keyword twoDepthChild = createChildKeyword(oneDepthChild, "map()의 파라미터", "파라미터 설명");

        createQuiz(oneDepthChild, "map을 왜 쓰나요?");
        createQuiz(root2, "컬렉션을 왜 쓰나요?");

        //when
        final KeywordsResponse response = roadMapService.findAllKeywordsWithProgress(curriculumId, null);

        //then
        assertSoftly(softAssertions -> {
            final ProxyableListAssert<KeywordResponse> roots = softAssertions.assertThat(response.getData());
            final AbstractListAssert<?, List<? extends KeywordResponse>, KeywordResponse, ObjectAssert<KeywordResponse>> oneDepthChildren
                = roots.flatMap(KeywordResponse::getChildrenKeywords);
            final AbstractListAssert<?, List<? extends KeywordResponse>, KeywordResponse, ObjectAssert<KeywordResponse>> twoDepthChildren
                = oneDepthChildren.flatMap(KeywordResponse::getChildrenKeywords);

            roots.hasSize(2)
                .satisfies(containsKeywordIds(root1.getId(), root2.getId()))
                .satisfies(containsTotalQuizCounts(0, 1))
                .satisfies(containsAnsweredQuizCounts(0, 0));

            oneDepthChildren.hasSize(1)
                .satisfies(containsKeywordIds(oneDepthChild.getId()))
                .satisfies(containsTotalQuizCounts(1))
                .satisfies(containsAnsweredQuizCounts(0));

            twoDepthChildren.hasSize(1)
                .satisfies(containsKeywordIds(twoDepthChild.getId()))
                .satisfies(containsTotalQuizCounts(0))
                .satisfies(containsAnsweredQuizCounts(0));
        });
    }

    @Test
    @DisplayName("커리큘럼 ID에 해당하는 모든 키워드를 진도율과 함께 조회할 수 있다 - 회원")
    void findAllKeywordsWithProgress_login() {
        //given
        final Long curriculumId = 1L;
        final Long session1 = createSession(curriculumId);
        final Long session2 = createSession(curriculumId);

        final Keyword root1 = createParentKeyword("스트림", "스트림 API 설명", session1);
        final Keyword root2 = createParentKeyword("컬렉션", "컬렉션 API 설명", session2);

        final Keyword oneDepthChild = createChildKeyword(root1, "map()", "스트림 api의 map");
        final Keyword twoDepthChild = createChildKeyword(oneDepthChild, "map()의 파라미터", "파라미터 설명");

        final Quiz quiz1 = createQuiz(root2, "컬렉션을 왜 쓰나요?");
        final Quiz quiz2 = createQuiz(twoDepthChild, "파라미터의 종류는?");
        createQuiz(oneDepthChild, "map을 왜 쓰나요?");

        final Member member = createMember();

        createEssayAnswer(quiz1, member, "배열을 쓸 수는 없으니까요");
        createEssayAnswer(quiz2, member, "Function<? super 현재 타입, ?> 입니다");

        //when
        final KeywordsResponse response = roadMapService.findAllKeywordsWithProgress(curriculumId, member.getId());

        //then
        assertSoftly(softAssertions -> {
            final ProxyableListAssert<KeywordResponse> roots = softAssertions.assertThat(response.getData());
            final AbstractListAssert<?, List<? extends KeywordResponse>, KeywordResponse, ObjectAssert<KeywordResponse>> oneDepthChildren
                = roots.flatMap(KeywordResponse::getChildrenKeywords);
            final AbstractListAssert<?, List<? extends KeywordResponse>, KeywordResponse, ObjectAssert<KeywordResponse>> twoDepthChildren
                = oneDepthChildren.flatMap(KeywordResponse::getChildrenKeywords);

            roots.hasSize(2)
                .satisfies(containsKeywordIds(root1.getId(), root2.getId()))
                .satisfies(containsTotalQuizCounts(0, 1))
                .satisfies(containsAnsweredQuizCounts(0, 1));

            oneDepthChildren.hasSize(1)
                .satisfies(containsKeywordIds(oneDepthChild.getId()))
                .satisfies(containsTotalQuizCounts(1))
                .satisfies(containsAnsweredQuizCounts(0));

            twoDepthChildren.hasSize(1)
                .satisfies(containsKeywordIds(twoDepthChild.getId()))
                .satisfies(containsTotalQuizCounts(1))
                .satisfies(containsAnsweredQuizCounts(1));
        });
    }

    private Consumer<List<? extends KeywordResponse>> containsAnsweredQuizCounts(final Integer... expected) {
        return keywords -> assertThat(keywords)
            .map(KeywordResponse::getAnsweredQuizCount)
            .containsExactlyInAnyOrder(expected);
    }

    private Consumer<List<? extends KeywordResponse>> containsTotalQuizCounts(final Integer... expected) {
        return keywords -> assertThat(keywords)
            .map(KeywordResponse::getTotalQuizCount)
            .containsExactlyInAnyOrder(expected);
    }

    private Consumer<List<? extends KeywordResponse>> containsKeywordIds(final Long... expected) {
        return keywords -> assertThat(keywords)
            .map(KeywordResponse::getKeywordId)
            .containsExactlyInAnyOrder(expected);
    }

    private Long createSession(final Long curriculumId) {
        Session session = new Session(curriculumId, "테스트 세션");
        sessionRepository.save(session);
        return session.getId();
    }

    private Keyword createParentKeyword(final String name, final String description, final Long sessionId) {
        final Keyword keyword = new Keyword(null, name, description, 1, 1, sessionId, null, emptySet());
        return keywordRepository.save(keyword);
    }

    private Keyword createChildKeyword(final Keyword parent, final String name, final String description) {
        final Keyword keyword = new Keyword(null, name, description, 1, 1, parent.getSessionId(), parent, emptySet());
        return keywordRepository.save(keyword);
    }

    private Quiz createQuiz(final Keyword keyword, final String question) {
        final Quiz quiz = new Quiz(keyword, question);
        return quizRepository.save(quiz);
    }

    private Member createMember() {
        final Member member = new Member("id", "연어", Role.CREW, 1L, "image");
        return memberRepository.save(member);
    }

    private EssayAnswer createEssayAnswer(final Quiz quiz, final Member member, final String answer) {
        final EssayAnswer essayAnswer = new EssayAnswer(quiz, answer, member);
        return essayAnswerRepository.save(essayAnswer);
    }
}
