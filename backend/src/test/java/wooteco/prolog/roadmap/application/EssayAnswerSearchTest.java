package wooteco.prolog.roadmap.application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.TestConstructor.AutowireMode;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.member.domain.Role;
import wooteco.prolog.roadmap.application.dto.EssayAnswerRequest;
import wooteco.prolog.roadmap.application.dto.EssayAnswerSearchRequest;
import wooteco.prolog.roadmap.domain.Curriculum;
import wooteco.prolog.roadmap.domain.Keyword;
import wooteco.prolog.roadmap.domain.Quiz;
import wooteco.prolog.roadmap.domain.repository.CurriculumRepository;
import wooteco.prolog.roadmap.domain.repository.KeywordRepository;
import wooteco.prolog.roadmap.domain.repository.QuizRepository;
import wooteco.prolog.session.domain.Session;
import wooteco.prolog.session.domain.repository.SessionRepository;

@SpringBootTest
@TestConstructor(autowireMode = AutowireMode.ALL)
public class EssayAnswerSearchTest {

    private final CurriculumRepository curriculumRepository;
    private final SessionRepository sessionRepository;
    private final KeywordRepository keywordRepository;
    private final QuizRepository quizRepository;
    private final EssayAnswerService essayAnswerService;

    public EssayAnswerSearchTest(CurriculumRepository curriculumRepository,
                                 SessionRepository sessionRepository,
                                 KeywordRepository keywordRepository, QuizRepository quizRepository,
                                 EssayAnswerService essayAnswerService) {
        this.curriculumRepository = curriculumRepository;
        this.sessionRepository = sessionRepository;
        this.keywordRepository = keywordRepository;
        this.quizRepository = quizRepository;
        this.essayAnswerService = essayAnswerService;
    }

    private Curriculum curriculum;
    private Session session1, session2;
    private Keyword keyword1, keyword2, keyword3, keyword4;
    private Quiz quiz1, quiz2, quiz3, quiz4, quiz5, quiz6, quiz7, quiz8, quiz9;
    private Member member;
    private Long essayAnswerId1, essayAnswerId2, essayAnswerId3, essayAnswerId4, essayAnswerId5,
        essayAnswerId6, essayAnswerId7, essayAnswerId8, essayAnswerId9, essayAnswerId10,
        essayAnswerId11, essayAnswerId12, essayAnswerId13, essayAnswerId14, essayAnswerId15,
        essayAnswerId16, essayAnswerId17, essayAnswerId18, essayAnswerId19, essayAnswerId20;

    @BeforeEach
    void init() {
        curriculum = curriculumRepository.save(new Curriculum("커리큘럼1"));
        session1 = sessionRepository.save(new Session(curriculum.getId(), "세션1"));
        session2 = sessionRepository.save(new Session(curriculum.getId(), "세션2"));

        keyword1 = Keyword.createKeyword("자바", "자바 설명", 1, 5, session1.getId(), null);
        keyword2 = Keyword.createKeyword("키워드", "키워드 설명", 2, 5, session2.getId(), null);
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

        member = new Member(11L, "username", "nickname", Role.CREW, 101L, "https://");

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

        essayAnswerId1 = essayAnswerService.createEssayAnswer(essayAnswer1, member.getId());
        essayAnswerId2 = essayAnswerService.createEssayAnswer(essayAnswer2, member.getId());
        essayAnswerId3 = essayAnswerService.createEssayAnswer(essayAnswer3, member.getId());
        essayAnswerId4 = essayAnswerService.createEssayAnswer(essayAnswer4, member.getId());
        essayAnswerId5 = essayAnswerService.createEssayAnswer(essayAnswer5, member.getId());
        essayAnswerId6 = essayAnswerService.createEssayAnswer(essayAnswer6, member.getId());
        essayAnswerId7 = essayAnswerService.createEssayAnswer(essayAnswer7, member.getId());
        essayAnswerId8 = essayAnswerService.createEssayAnswer(essayAnswer8, member.getId());
        essayAnswerId9 = essayAnswerService.createEssayAnswer(essayAnswer9, member.getId());
        essayAnswerId10 = essayAnswerService.createEssayAnswer(essayAnswer10, member.getId());
        essayAnswerId11 = essayAnswerService.createEssayAnswer(essayAnswer11, member.getId());
        essayAnswerId12 = essayAnswerService.createEssayAnswer(essayAnswer12, member.getId());
        essayAnswerId13 = essayAnswerService.createEssayAnswer(essayAnswer13, member.getId());
        essayAnswerId14 = essayAnswerService.createEssayAnswer(essayAnswer14, member.getId());
        essayAnswerId15 = essayAnswerService.createEssayAnswer(essayAnswer15, member.getId());
        essayAnswerId16 = essayAnswerService.createEssayAnswer(essayAnswer16, member.getId());
        essayAnswerId17 = essayAnswerService.createEssayAnswer(essayAnswer17, member.getId());
        essayAnswerId18 = essayAnswerService.createEssayAnswer(essayAnswer18, member.getId());
        essayAnswerId19 = essayAnswerService.createEssayAnswer(essayAnswer19, member.getId());
        essayAnswerId20 = essayAnswerService.createEssayAnswer(essayAnswer20, member.getId());
    }
}
