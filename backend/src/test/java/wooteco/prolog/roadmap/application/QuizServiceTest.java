package wooteco.prolog.roadmap.application;

import javax.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.TestConstructor;
import wooteco.prolog.roadmap.application.dto.QuizRequest;
import wooteco.prolog.roadmap.application.dto.QuizResponse;
import wooteco.prolog.roadmap.domain.Keyword;
import wooteco.prolog.roadmap.domain.Quiz;
import wooteco.prolog.roadmap.domain.repository.KeywordRepository;
import wooteco.prolog.roadmap.domain.repository.QuizRepository;
import wooteco.prolog.roadmap.exception.QuizNotFoundException;
import wooteco.prolog.session.domain.Session;
import wooteco.prolog.session.domain.repository.SessionRepository;
import wooteco.support.utils.NewIntegrationTest;

@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@NewIntegrationTest
class QuizServiceTest {

    private final KeywordRepository keywordRepository;
    private final QuizRepository quizRepository;
    private final SessionRepository sessionRepository;
    private final QuizService quizService;
    private final EntityManager em;

    public QuizServiceTest(KeywordRepository keywordRepository,
                           QuizRepository quizRepository,
                           SessionRepository sessionRepository,
                           QuizService quizService, EntityManager em) {
        this.keywordRepository = keywordRepository;
        this.quizRepository = quizRepository;
        this.sessionRepository = sessionRepository;
        this.quizService = quizService;
        this.em = em;
    }

    private Session session_백엔드_레벨1;

    private Keyword 자바;
    private Keyword 깃;

    private Quiz 자바_질문1;
    private Quiz 자바_질문2;
    private Quiz 깃_질문1;

    private QuizRequest 퀴즈_요청 = new QuizRequest("자바를 자바바~");
    private QuizRequest 퀴즈_수정_요청 = new QuizRequest("수달 천재 자바왕~");

    @BeforeEach
    void setUp() {
        session_백엔드_레벨1 = sessionRepository.save(new Session("백엔드Java 레벨1"));

        자바 = keywordRepository.save(
            new Keyword(null, "자바", "자바입니다", 1, 1, session_백엔드_레벨1.getId(), null, null));
        session_백엔드_레벨1 = sessionRepository.save(new Session("백엔드Java 레벨1"));

        깃 = keywordRepository.save(
            new Keyword(null, "깃", "깃입니다", 2, 2, session_백엔드_레벨1.getId(), null, null));
        session_백엔드_레벨1 = sessionRepository.save(new Session("백엔드Java 레벨1"));

        자바_질문1 = quizRepository.save(new Quiz(자바, "자바의 아버지는 제임스 고슬링일까요 ? 제이슨일까요 ?"));
        자바_질문2 = quizRepository.save(new Quiz(자바, "Stream 은 자바 몇 버전부터 지원했을까요?"));
        깃_질문1 = quizRepository.save(new Quiz(깃, "깃 레포지토리를 날려버리면 복구가 가능할까요?"));
    }


    @Test
    @DisplayName("없는 키워드에 퀴즈를 생성하면 예외를 반환한다")
    void create_false_not_found_keyword() {
        final Long 없는_세션_ID = 1000L;
        Assertions.assertThatThrownBy(() -> quizService.createQuiz(없는_세션_ID, 퀴즈_요청));
    }

    @Test
    @DisplayName("퀴즈를 생성한다.")
    void create() {
        final Long quizId = quizService.createQuiz(자바.getId(), 퀴즈_요청);

        Assertions.assertThat(quizId).isNotNull();
    }

    @Test
    @DisplayName("퀴즈를 수정한다.")
    void update() {
        final Long quizId = quizService.createQuiz(자바.getId(), 퀴즈_요청);

        quizService.updateQuiz(quizId, 퀴즈_수정_요청);
        quizRepository.flush();
        em.clear();

        final QuizResponse response = quizService.findById(quizId);
        Assertions.assertThat(response.getQuestion()).isEqualTo(퀴즈_수정_요청.getQuestion());
    }

    @Test
    @DisplayName("퀴즈를 삭제한다.")
    void delete() {
        final Long quizId = quizService.createQuiz(자바.getId(), 퀴즈_요청);

        quizService.deleteQuiz(quizId);

        Assertions.assertThat(quizRepository.existsById(quizId)).isFalse();
    }

    @Test
    @DisplayName("없는 퀴즈를 삭제하면 예외가 발생한다.")
    void delete_false() {
        final Long 없는_퀴즈_id = 10000L;

        Assertions.assertThatThrownBy(() -> quizService.deleteQuiz(없는_퀴즈_id))
            .isInstanceOf(QuizNotFoundException.class);
    }
}
