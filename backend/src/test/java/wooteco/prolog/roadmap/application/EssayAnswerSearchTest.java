package wooteco.prolog.roadmap.application;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.TestConstructor.AutowireMode;
import org.springframework.transaction.annotation.Transactional;
import wooteco.prolog.member.application.dto.MemberResponse;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.member.domain.Role;
import wooteco.prolog.member.domain.repository.MemberRepository;
import wooteco.prolog.roadmap.application.dto.EssayAnswerRequest;
import wooteco.prolog.roadmap.application.dto.EssayAnswerResponse;
import wooteco.prolog.roadmap.application.dto.EssayAnswerSearchRequest;
import wooteco.prolog.roadmap.application.dto.QuizResponse;
import wooteco.prolog.roadmap.domain.Curriculum;
import wooteco.prolog.roadmap.domain.Keyword;
import wooteco.prolog.roadmap.domain.Quiz;
import wooteco.prolog.roadmap.domain.repository.CurriculumRepository;
import wooteco.prolog.roadmap.domain.repository.KeywordRepository;
import wooteco.prolog.roadmap.domain.repository.QuizRepository;
import wooteco.prolog.session.domain.Session;
import wooteco.prolog.session.domain.repository.SessionRepository;
import wooteco.prolog.studylog.application.dto.EssayAnswersResponse;

@SpringBootTest
@Transactional
@TestConstructor(autowireMode = AutowireMode.ALL)
public class EssayAnswerSearchTest {

    private CurriculumRepository curriculumRepository;
    private SessionRepository sessionRepository;
    private KeywordRepository keywordRepository;
    private QuizRepository quizRepository;
    private MemberRepository memberRepository;
    private EssayAnswerService essayAnswerService;

    public EssayAnswerSearchTest(CurriculumRepository curriculumRepository,
                                 SessionRepository sessionRepository,
                                 KeywordRepository keywordRepository,
                                 QuizRepository quizRepository, MemberRepository memberRepository,
                                 EssayAnswerService essayAnswerService) {
        this.curriculumRepository = curriculumRepository;
        this.sessionRepository = sessionRepository;
        this.keywordRepository = keywordRepository;
        this.quizRepository = quizRepository;
        this.memberRepository = memberRepository;
        this.essayAnswerService = essayAnswerService;
    }

    private Curriculum curriculum;
    private Keyword keyword1, keyword2, keyword3, keyword4;
    private Quiz quiz1, quiz2, quiz3, quiz4, quiz5, quiz6, quiz7, quiz8, quiz9;
    private Member member1, member2, member3, member4;
    private Long essayAnswerId1, essayAnswerId2, essayAnswerId3, essayAnswerId4, essayAnswerId5,
        essayAnswerId6, essayAnswerId7, essayAnswerId8, essayAnswerId9, essayAnswerId10,
        essayAnswerId11, essayAnswerId12, essayAnswerId13, essayAnswerId14, essayAnswerId15,
        essayAnswerId16, essayAnswerId17, essayAnswerId18, essayAnswerId19, essayAnswerId20;

    @BeforeEach
    void init() {
        curriculum = curriculumRepository.save(new Curriculum("커리큘럼1"));
        Session session1 = sessionRepository.save(new Session(curriculum.getId(), "세션1"));
        Session session2 = sessionRepository.save(new Session(curriculum.getId(), "세션2"));

        keyword1 = keywordRepository.save(
            Keyword.createKeyword("자바", "자바 설명", 1, 5, session1.getId(), null));
        keyword2 = keywordRepository.save(
            Keyword.createKeyword("키워드", "키워드 설명", 2, 5, session2.getId(), null));
        keyword3 = keywordRepository.save(
            Keyword.createKeyword("자바3", "자바 설명3", 3, 5, session1.getId(), null));
        keyword4 = keywordRepository.save(
            Keyword.createKeyword("자바4", "자바 설명4", 4, 5, session2.getId(), null));

        quiz1 = quizRepository.save(new Quiz(keyword1, "퀴즈1"));
        quiz2 = quizRepository.save(new Quiz(keyword2, "퀴즈2"));
        quiz3 = quizRepository.save(new Quiz(keyword2, "퀴즈3"));
        quiz4 = quizRepository.save(new Quiz(keyword3, "퀴즈4"));
        quiz5 = quizRepository.save(new Quiz(keyword3, "퀴즈5"));
        quiz6 = quizRepository.save(new Quiz(keyword4, "퀴즈6"));
        quiz7 = quizRepository.save(new Quiz(keyword4, "퀴즈7"));
        quiz8 = quizRepository.save(new Quiz(keyword4, "퀴즈8"));
        quiz9 = quizRepository.save(new Quiz(keyword4, "퀴즈9"));

        member1 = memberRepository.save(
            new Member(11L, "username1", "nickname1", Role.CREW, 111L, "https://"));
        member2 = memberRepository.save(
            new Member(12L, "username2", "nickname2", Role.CREW, 112L, "https://"));
        member3 = memberRepository.save(
            new Member(13L, "username3", "nickname3", Role.CREW, 113L, "https://"));
        member4 = memberRepository.save(
            new Member(14L, "username4", "nickname4", Role.CREW, 115L, "https://"));

        final EssayAnswerRequest essayAnswer1 = new EssayAnswerRequest(quiz1.getId(), "대답1");
        final EssayAnswerRequest essayAnswer2 = new EssayAnswerRequest(quiz2.getId(), "대답2");
        final EssayAnswerRequest essayAnswer3 = new EssayAnswerRequest(quiz2.getId(), "대답3");
        final EssayAnswerRequest essayAnswer4 = new EssayAnswerRequest(quiz2.getId(), "대답4");
        final EssayAnswerRequest essayAnswer5 = new EssayAnswerRequest(quiz2.getId(), "대답5");
        final EssayAnswerRequest essayAnswer6 = new EssayAnswerRequest(quiz3.getId(), "대답6");
        final EssayAnswerRequest essayAnswer7 = new EssayAnswerRequest(quiz4.getId(), "대답7");
        final EssayAnswerRequest essayAnswer8 = new EssayAnswerRequest(quiz4.getId(), "대답8");
        final EssayAnswerRequest essayAnswer9 = new EssayAnswerRequest(quiz5.getId(), "대답9");
        final EssayAnswerRequest essayAnswer10 = new EssayAnswerRequest(quiz5.getId(), "대답10");
        final EssayAnswerRequest essayAnswer11 = new EssayAnswerRequest(quiz6.getId(), "대답11");
        final EssayAnswerRequest essayAnswer12 = new EssayAnswerRequest(quiz6.getId(), "대답12");
        final EssayAnswerRequest essayAnswer13 = new EssayAnswerRequest(quiz6.getId(), "대답13");
        final EssayAnswerRequest essayAnswer14 = new EssayAnswerRequest(quiz7.getId(), "대답14");
        final EssayAnswerRequest essayAnswer15 = new EssayAnswerRequest(quiz7.getId(), "대답15");
        final EssayAnswerRequest essayAnswer16 = new EssayAnswerRequest(quiz8.getId(), "대답16");
        final EssayAnswerRequest essayAnswer17 = new EssayAnswerRequest(quiz8.getId(), "대답17");
        final EssayAnswerRequest essayAnswer18 = new EssayAnswerRequest(quiz9.getId(), "대답18");
        final EssayAnswerRequest essayAnswer19 = new EssayAnswerRequest(quiz9.getId(), "대답19");
        final EssayAnswerRequest essayAnswer20 = new EssayAnswerRequest(quiz9.getId(), "대답20");

        essayAnswerId1 = essayAnswerService.createEssayAnswer(essayAnswer1, member1.getId());
        essayAnswerId2 = essayAnswerService.createEssayAnswer(essayAnswer2, member1.getId());
        essayAnswerId3 = essayAnswerService.createEssayAnswer(essayAnswer3, member2.getId());
        essayAnswerId4 = essayAnswerService.createEssayAnswer(essayAnswer4, member3.getId());
        essayAnswerId5 = essayAnswerService.createEssayAnswer(essayAnswer5, member4.getId());
        essayAnswerId6 = essayAnswerService.createEssayAnswer(essayAnswer6, member1.getId());
        essayAnswerId7 = essayAnswerService.createEssayAnswer(essayAnswer7, member1.getId());
        essayAnswerId8 = essayAnswerService.createEssayAnswer(essayAnswer8, member2.getId());
        essayAnswerId9 = essayAnswerService.createEssayAnswer(essayAnswer9, member1.getId());
        essayAnswerId10 = essayAnswerService.createEssayAnswer(essayAnswer10, member2.getId());
        essayAnswerId11 = essayAnswerService.createEssayAnswer(essayAnswer11, member1.getId());
        essayAnswerId12 = essayAnswerService.createEssayAnswer(essayAnswer12, member2.getId());
        essayAnswerId13 = essayAnswerService.createEssayAnswer(essayAnswer13, member3.getId());
        essayAnswerId14 = essayAnswerService.createEssayAnswer(essayAnswer14, member1.getId());
        essayAnswerId15 = essayAnswerService.createEssayAnswer(essayAnswer15, member2.getId());
        essayAnswerId16 = essayAnswerService.createEssayAnswer(essayAnswer16, member1.getId());
        essayAnswerId17 = essayAnswerService.createEssayAnswer(essayAnswer17, member2.getId());
        essayAnswerId18 = essayAnswerService.createEssayAnswer(essayAnswer18, member1.getId());
        essayAnswerId19 = essayAnswerService.createEssayAnswer(essayAnswer19, member2.getId());
        essayAnswerId20 = essayAnswerService.createEssayAnswer(essayAnswer20, member3.getId());
    }

    @Test
    @DisplayName("커리큘럼 아이디를 받아 답변을 검색한다.")
    void 커리큘럼_아이디를_받아서_답변을_검색한다() {
        // given
        final EssayAnswerSearchRequest 답변_검색_요청 = new EssayAnswerSearchRequest(
            curriculum.getId(), null, null, null);

        // when
        final EssayAnswersResponse 답변_응답들 = essayAnswerService.searchEssayAnswers(
            답변_검색_요청, PageRequest.of(0, 10));

        // then
        final List<EssayAnswerResponse> 예상_답변_응답_리스트 = Arrays.asList(
            예상_답변_응답(essayAnswerId20, quiz9, "대답20", member3),
            예상_답변_응답(essayAnswerId19, quiz9, "대답19", member2),
            예상_답변_응답(essayAnswerId18, quiz9, "대답18", member1),
            예상_답변_응답(essayAnswerId17, quiz8, "대답17", member2),
            예상_답변_응답(essayAnswerId16, quiz8, "대답16", member1),
            예상_답변_응답(essayAnswerId15, quiz7, "대답15", member2),
            예상_답변_응답(essayAnswerId14, quiz7, "대답14", member1),
            예상_답변_응답(essayAnswerId13, quiz6, "대답13", member3),
            예상_답변_응답(essayAnswerId12, quiz6, "대답12", member2),
            예상_답변_응답(essayAnswerId11, quiz6, "대답11", member1)
        );
        final EssayAnswersResponse 예상되는_답변_응답들 = new EssayAnswersResponse(예상_답변_응답_리스트, 20L, 2, 1);

        assertThat(답변_응답들)
            .usingRecursiveComparison()
            .ignoringFields("data.createdAt", "data.updatedAt")
            .isEqualTo(예상되는_답변_응답들);
    }

    @Test
    @DisplayName("커리큘럼 아이디와 키워드 아이디를 받아서 답변을 검색한다.")
    void 커리큘럼_아이디와_키워드_아이디를_받아서_답변을_검색한다() {
        // given
        final EssayAnswerSearchRequest 답변_검색_요청 = new EssayAnswerSearchRequest(
            curriculum.getId(), keyword3.getId(), null, null);

        // when
        final EssayAnswersResponse 답변_응답들 = essayAnswerService.searchEssayAnswers(
            답변_검색_요청, PageRequest.of(0, 10));

        // then
        final List<EssayAnswerResponse> 예상_답변_응답_리스트 = Arrays.asList(
            예상_답변_응답(essayAnswerId10, quiz5, "대답10", member2),
            예상_답변_응답(essayAnswerId9, quiz5, "대답9", member1),
            예상_답변_응답(essayAnswerId8, quiz4, "대답8", member2),
            예상_답변_응답(essayAnswerId7, quiz4, "대답7", member1)
        );

        final EssayAnswersResponse 예상되는_답변_응답들 = new EssayAnswersResponse(예상_답변_응답_리스트, 4L, 1, 1);

        assertThat(답변_응답들)
            .usingRecursiveComparison()
            .ignoringFields("data.createdAt", "data.updatedAt")
            .isEqualTo(예상되는_답변_응답들);
    }

    @Test
    @DisplayName("커리큘럼 아이디와 키워드 아이디와 퀴즈 아이디를 받아서 답변을 검색한다.")
    void 커리큘럼_아이디와_키워드_아이디와_퀴즈_아이디를_받아서_답변을_검색한다() {
        // given
        final EssayAnswerSearchRequest 답변_검색_요청 = new EssayAnswerSearchRequest(
            curriculum.getId(), keyword4.getId(),
            Arrays.asList(quiz6.getId(), quiz9.getId()), null);

        // when
        final EssayAnswersResponse 답변_응답들 = essayAnswerService.searchEssayAnswers(
            답변_검색_요청, PageRequest.of(0, 10));

        // then
        final List<EssayAnswerResponse> 예상_답변_응답_리스트 = Arrays.asList(
            예상_답변_응답(essayAnswerId20, quiz9, "대답20", member3),
            예상_답변_응답(essayAnswerId19, quiz9, "대답19", member2),
            예상_답변_응답(essayAnswerId18, quiz9, "대답18", member1),
            예상_답변_응답(essayAnswerId13, quiz6, "대답13", member3),
            예상_답변_응답(essayAnswerId12, quiz6, "대답12", member2),
            예상_답변_응답(essayAnswerId11, quiz6, "대답11", member1)
        );

        final EssayAnswersResponse 예상되는_답변_응답들 = new EssayAnswersResponse(예상_답변_응답_리스트, 6L, 1, 1);

        assertThat(답변_응답들)
            .usingRecursiveComparison()
            .ignoringFields("data.createdAt", "data.updatedAt")
            .isEqualTo(예상되는_답변_응답들);
    }

    private EssayAnswerResponse 예상_답변_응답(final Long essayAnswerId, final Quiz quiz,
                                         final String answer, final Member member) {
        return new EssayAnswerResponse(essayAnswerId,
            new QuizResponse(quiz.getId(), quiz.getQuestion()),
            answer, new MemberResponse(member.getId(), member.getUsername(), member.getNickname(),
            Role.CREW, "https://"), LocalDateTime.now(), LocalDateTime.now());
    }

    @DisplayName("searchEssayAnswers() : 키워드 id가 있고, 퀴즈 id는 없으며, "
        + "사용자의 id가 주어지면 해당 키워드의 답변들 중에 해당 사용자가 작성한 글을 조회할 수 있다.")
    @Test
    void test_searchEssayAnswers_keywordId_noQuizId_memberId() {
        // given
        final EssayAnswerSearchRequest request = new EssayAnswerSearchRequest(
            curriculum.getId(),
            keyword4.getId(),
            null,
            Arrays.asList(member1.getId())
        );

        // when
        final EssayAnswersResponse actual = essayAnswerService.searchEssayAnswers(
            request, PageRequest.of(0, 10));

        // then
        final List<EssayAnswerResponse> expected = Arrays.asList(
            예상_답변_응답(essayAnswerId18, quiz9, "대답18", member1),
            예상_답변_응답(essayAnswerId16, quiz8, "대답16", member1),
            예상_답변_응답(essayAnswerId14, quiz7, "대답14", member1),
            예상_답변_응답(essayAnswerId11, quiz6, "대답11", member1)
        );

        final EssayAnswersResponse response = new EssayAnswersResponse(expected, 4L, 1, 1);

        assertThat(actual)
            .usingRecursiveComparison()
            .ignoringFields("data.createdAt", "data.updatedAt")
            .isEqualTo(response);
    }

    @DisplayName("searchEssayAnswers() : 키워드 id 있고, 퀴즈 id 없고, "
        + "사용자의 id가 주어지지 않으면 해당 키워드의 답변들을 모두 조회할 수 있다.")
    @Test
    void test_searchEssayAnswers_keywordId_NoQuizId_noMemberId() {
        // given
        final EssayAnswerSearchRequest request = new EssayAnswerSearchRequest(
            curriculum.getId(),
            keyword2.getId(),
            null,
            null
        );

        // when
        final EssayAnswersResponse actual = essayAnswerService.searchEssayAnswers(
            request, PageRequest.of(0, 10));

        // then
        final List<EssayAnswerResponse> expected = Arrays.asList(
            예상_답변_응답(essayAnswerId6, quiz3, "대답6", member1),
            예상_답변_응답(essayAnswerId5, quiz2, "대답5", member4),
            예상_답변_응답(essayAnswerId4, quiz2, "대답4", member3),
            예상_답변_응답(essayAnswerId3, quiz2, "대답3", member2),
            예상_답변_응답(essayAnswerId2, quiz2, "대답2", member1)
        );

        final EssayAnswersResponse response = new EssayAnswersResponse(expected, 5L, 1, 1);

        assertThat(actual)
            .usingRecursiveComparison()
            .ignoringFields("data.createdAt", "data.updatedAt")
            .isEqualTo(response);
    }

    @DisplayName("searchEssayAnswers() : 키워드 id 있고, 퀴즈 id 있고, "
        + "사용자의 id가 주어지면 사용자가 작성한 해당 퀴즈의 답변들을 모두 조회할 수 있다.")
    @Test
    void test_searchEssayAnswers_keywordId_quizId_memberId() {
        // given
        final EssayAnswerSearchRequest request = new EssayAnswerSearchRequest(
            curriculum.getId(),
            keyword4.getId(),
            Arrays.asList(quiz6.getId(), quiz7.getId()),
            Arrays.asList(member1.getId())
        );

        // when
        final EssayAnswersResponse actual = essayAnswerService.searchEssayAnswers(
            request, PageRequest.of(0, 10));

        // then
        final List<EssayAnswerResponse> expected = Arrays.asList(
            예상_답변_응답(essayAnswerId14, quiz7, "대답14", member1),
            예상_답변_응답(essayAnswerId11, quiz6, "대답11", member1)
        );

        final EssayAnswersResponse response = new EssayAnswersResponse(expected, 2L, 1, 1);

        assertThat(actual)
            .usingRecursiveComparison()
            .ignoringFields("data.createdAt", "data.updatedAt")
            .isEqualTo(response);
    }

    @DisplayName("searchEssayAnswers() : 키워드 id 있고, 퀴즈 id 있고, "
        + "사용자의 id가 주어지지 않으면 해당 퀴즈의 답변들을 모두 조회할 수 있다.")
    @Test
    void test_searchEssayAnswers_keywordId_quizId_noMemberId() {
        // given
        final EssayAnswerSearchRequest request = new EssayAnswerSearchRequest(
            curriculum.getId(),
            keyword4.getId(),
            Arrays.asList(quiz6.getId(), quiz7.getId()),
            null
        );

        // when
        final EssayAnswersResponse actual = essayAnswerService.searchEssayAnswers(
            request, PageRequest.of(0, 10));

        // then
        final List<EssayAnswerResponse> expected = Arrays.asList(
            예상_답변_응답(essayAnswerId15, quiz7, "대답15", member2),
            예상_답변_응답(essayAnswerId14, quiz7, "대답14", member1),
            예상_답변_응답(essayAnswerId13, quiz6, "대답13", member3),
            예상_답변_응답(essayAnswerId12, quiz6, "대답12", member2),
            예상_답변_응답(essayAnswerId11, quiz6, "대답11", member1)
        );

        final EssayAnswersResponse response = new EssayAnswersResponse(expected, 5L, 1, 1);

        assertThat(actual)
            .usingRecursiveComparison()
            .ignoringFields("data.createdAt", "data.updatedAt")
            .isEqualTo(response);
    }
}
