package wooteco.prolog.roadmap.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.TestConstructor.AutowireMode;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.member.domain.Role;
import wooteco.prolog.member.domain.repository.MemberRepository;
import wooteco.prolog.roadmap.application.dto.EssayAnswerRequest;
import wooteco.prolog.roadmap.domain.EssayAnswer;
import wooteco.prolog.roadmap.domain.Keyword;
import wooteco.prolog.roadmap.domain.Quiz;
import wooteco.prolog.roadmap.domain.repository.EssayAnswerRepository;
import wooteco.prolog.roadmap.domain.repository.KeywordRepository;
import wooteco.prolog.roadmap.domain.repository.QuizRepository;
import wooteco.prolog.session.domain.Session;
import wooteco.prolog.session.domain.repository.SessionRepository;
import wooteco.support.utils.NewIntegrationTest;

@TestConstructor(autowireMode = AutowireMode.ALL)
@NewIntegrationTest
class EssayAnswerServiceTest {

    private final EssayAnswerService essayAnswerService;
    private final EssayAnswerRepository essayAnswerRepository;
    private final SessionRepository sessionRepository;
    private final KeywordRepository keywordRepository;
    private final QuizRepository quizRepository;
    private final MemberRepository memberRepository;
    private final EntityManager entityManager;

    public EssayAnswerServiceTest(EssayAnswerService essayAnswerService,
                                  EssayAnswerRepository essayAnswerRepository,
                                  SessionRepository sessionRepository,
                                  KeywordRepository keywordRepository,
                                  QuizRepository quizRepository, MemberRepository memberRepository,
                                  EntityManager entityManager) {
        this.essayAnswerService = essayAnswerService;
        this.essayAnswerRepository = essayAnswerRepository;
        this.sessionRepository = sessionRepository;
        this.keywordRepository = keywordRepository;
        this.quizRepository = quizRepository;
        this.memberRepository = memberRepository;
        this.entityManager = entityManager;
    }

    private Quiz 자바_질문;
    private Member 회원;

    @BeforeEach
    void setUp() {
        Session session_백엔드_레벨1 = sessionRepository.save(new Session("백엔드Java 레벨1"));

        Keyword 자바 = keywordRepository.save(
            new Keyword(null, "자바", "자바입니다", 1, 1, session_백엔드_레벨1.getId(), null, null));

        자바_질문 = quizRepository.save(new Quiz(자바, "자바 언어의 특징은?"));

        회원 = memberRepository.save(new Member("username", "nickname", Role.CREW, 101L, "https://"));
    }

    @Test
    @DisplayName("질문에 대한 답변을 작성한다.")
    void create() {
        EssayAnswerRequest request = new EssayAnswerRequest(자바_질문.getId(), "객체 지향 언어");
        Long essayAnswerId = essayAnswerService.createEssayAnswer(request, 회원.getId());

        assertThat(essayAnswerId).isNotNull();
    }

    @Test
    @DisplayName("답변을 수정한다.")
    void update() {
        EssayAnswerRequest request = new EssayAnswerRequest(자바_질문.getId(), "객체 지향 언어");
        Long essayAnswerId = essayAnswerService.createEssayAnswer(request, 회원.getId());
        String updatedAnswer = "OOP";

        essayAnswerService.updateEssayAnswer(essayAnswerId, updatedAnswer, 회원.getId());
        essayAnswerRepository.flush();
        entityManager.clear();

        EssayAnswer essayAnswer = essayAnswerService.getById(essayAnswerId);
        assertThat(essayAnswer.getAnswer()).isEqualTo(updatedAnswer);
    }

    @Test
    @DisplayName("답변을 삭제한다.")
    void delete() {
        EssayAnswerRequest request = new EssayAnswerRequest(자바_질문.getId(), "객체 지향 언어");
        Long essayAnswerId = essayAnswerService.createEssayAnswer(request, 회원.getId());

        essayAnswerService.deleteEssayAnswer(essayAnswerId, 회원.getId());

        assertThat(essayAnswerRepository.existsById(essayAnswerId)).isFalse();
    }

    @Test
    @DisplayName("존재하지 않는 답변을 삭제하면 실패한다.")
    void delete_fail() {
        assertThatThrownBy(
            () -> essayAnswerService.deleteEssayAnswer(101L, 회원.getId())).isInstanceOf(
            IllegalArgumentException.class).hasMessage("답변이 존재하지 않습니다. answerId=101");
    }
}
