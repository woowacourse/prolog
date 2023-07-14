package wooteco.prolog.roadmap.domain.repository;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.domain.Specification;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.member.domain.Role;
import wooteco.prolog.member.domain.repository.MemberRepository;
import wooteco.prolog.roadmap.domain.Curriculum;
import wooteco.prolog.roadmap.domain.EssayAnswer;
import wooteco.prolog.roadmap.domain.Keyword;
import wooteco.prolog.roadmap.domain.Quiz;
import wooteco.prolog.session.domain.Session;
import wooteco.prolog.session.domain.repository.SessionRepository;

@SpringBootTest
class EssayAnswerSpecificationTest {

    @Autowired
    EssayAnswerRepository essayAnswerRepository;

    @Autowired
    CurriculumRepository curriculumRepository;

    @Autowired
    KeywordRepository keywordRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    SessionRepository sessionRepository;
    @Autowired
    private QuizRepository quizRepository;

    @BeforeEach
    void init() {
        final Curriculum curriculum = curriculumRepository.save(new Curriculum("커리큘럼1"));
        final Session session1 = sessionRepository.save(new Session(curriculum.getId(), "세션1"));
        final Session session2 = sessionRepository.save(new Session(curriculum.getId(), "세션2"));

        final Keyword keyword1 = keywordRepository.save(
            Keyword.createKeyword("자바1", "자바 설명1", 1, 5, session1.getId(), null));
        final Keyword keyword2 = keywordRepository.save(
            Keyword.createKeyword("자바2", "자바 설명2", 2, 5, session1.getId(), null));
        final Keyword keyword3 = keywordRepository.save(
            Keyword.createKeyword("자바3", "자바 설명3", 3, 5, session1.getId(), null));
        final Keyword keyword4 = keywordRepository.save(
            Keyword.createKeyword("자바4", "자바 설명4", 4, 5, session2.getId(), null));

        final Quiz quiz1 = quizRepository.save(new Quiz(keyword1, "퀴즈1"));
        final Quiz quiz2 = quizRepository.save(new Quiz(keyword2, "퀴즈2"));
        final Quiz quiz3 = quizRepository.save(new Quiz(keyword2, "퀴즈3"));
        final Quiz quiz4 = quizRepository.save(new Quiz(keyword3, "퀴즈4"));
        final Quiz quiz5 = quizRepository.save(new Quiz(keyword3, "퀴즈5"));
        final Quiz quiz6 = quizRepository.save(new Quiz(keyword4, "퀴즈6"));
        final Quiz quiz7 = quizRepository.save(new Quiz(keyword4, "퀴즈7"));
        final Quiz quiz8 = quizRepository.save(new Quiz(keyword4, "퀴즈8"));
        final Quiz quiz9 = quizRepository.save(new Quiz(keyword4, "퀴즈9"));

        Member member = new Member(11L, "username", "nickname", Role.CREW, 101L, "https://");

        EssayAnswer essayAnswer1 = new EssayAnswer(quiz1, "대답1", member);
        EssayAnswer essayAnswer2 = new EssayAnswer(quiz2, "대답2", member);
        EssayAnswer essayAnswer3 = new EssayAnswer(quiz2, "대답3", member);
        EssayAnswer essayAnswer4 = new EssayAnswer(quiz2, "대답4", member);
        EssayAnswer essayAnswer5 = new EssayAnswer(quiz2, "대답5", member);
        EssayAnswer essayAnswer6 = new EssayAnswer(quiz3, "대답6", member);
        EssayAnswer essayAnswer7 = new EssayAnswer(quiz4, "대답7", member);
        EssayAnswer essayAnswer8 = new EssayAnswer(quiz4, "대답8", member);
        EssayAnswer essayAnswer9 = new EssayAnswer(quiz5, "대답9", member);
        EssayAnswer essayAnswer10 = new EssayAnswer(quiz5, "대답10", member);
        EssayAnswer essayAnswer11 = new EssayAnswer(quiz6, "대답11", member);
        EssayAnswer essayAnswer12 = new EssayAnswer(quiz6, "대답12", member);
        EssayAnswer essayAnswer13 = new EssayAnswer(quiz6, "대답13", member);
        EssayAnswer essayAnswer14 = new EssayAnswer(quiz7, "대답14", member);
        EssayAnswer essayAnswer15 = new EssayAnswer(quiz7, "대답15", member);
        EssayAnswer essayAnswer16 = new EssayAnswer(quiz8, "대답16", member);
        EssayAnswer essayAnswer17 = new EssayAnswer(quiz8, "대답17", member);
        EssayAnswer essayAnswer18 = new EssayAnswer(quiz9, "대답18", member);
        EssayAnswer essayAnswer19 = new EssayAnswer(quiz9, "대답19", member);
        EssayAnswer essayAnswer20 = new EssayAnswer(quiz9, "대답20", member);

        essayAnswerRepository.saveAll(
            Arrays.asList(
                essayAnswer1, essayAnswer2, essayAnswer3, essayAnswer4, essayAnswer5, essayAnswer6,
                essayAnswer7, essayAnswer8, essayAnswer9, essayAnswer10, essayAnswer11,
                essayAnswer12, essayAnswer13, essayAnswer14, essayAnswer15, essayAnswer16,
                essayAnswer17, essayAnswer18, essayAnswer19, essayAnswer20)
        );
    }

    @Test
    void aa() throws Exception {
        //given
        final Specification<EssayAnswer> essayAnswerSpecification = EssayAnswerSpecification.equalsSessionIdsIn(
            Arrays.asList(1L));

        //when
        final List<EssayAnswer> all = essayAnswerRepository.findAll(essayAnswerSpecification);

        //then
        System.out.println("all = " + all);
    }
}
