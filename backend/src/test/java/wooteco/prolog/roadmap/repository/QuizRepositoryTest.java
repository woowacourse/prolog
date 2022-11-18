package wooteco.prolog.roadmap.repository;

import java.util.Arrays;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import wooteco.prolog.roadmap.domain.Keyword;
import wooteco.prolog.roadmap.domain.Quiz;
import wooteco.prolog.roadmap.domain.repository.KeywordRepository;
import wooteco.prolog.roadmap.domain.repository.QuizRepository;
import wooteco.prolog.session.domain.Session;
import wooteco.prolog.session.domain.repository.SessionRepository;
import wooteco.support.utils.RepositoryTest;

@RepositoryTest
class QuizRepositoryTest {

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private KeywordRepository keywordRepository;

    @Autowired
    private SessionRepository sessionRepository;

    private Session session_백엔드_레벨1;

    private Keyword 자바;
    private Keyword 깃;

    private Quiz 자바_질문1;
    private Quiz 자바_질문2;
    private Quiz 깃_질문1;

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


    @DisplayName("키워드 id 로 퀴즈 List 를 조회한다.")
    @Test
    void findQuizzesByKeyword() {
        //given
        final List<Quiz> expect = Arrays.asList(자바_질문1, 자바_질문2);
        final List<Quiz> actual = quizRepository.findQuizByKeywordId(자바.getId());

        Assertions.assertThat(actual).containsExactlyElementsOf(expect);
    }
}
