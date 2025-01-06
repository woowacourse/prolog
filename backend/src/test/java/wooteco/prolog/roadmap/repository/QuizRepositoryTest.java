package wooteco.prolog.roadmap.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import wooteco.prolog.roadmap.application.dto.CurriculumQuizResponse;
import wooteco.prolog.roadmap.domain.Curriculum;
import wooteco.prolog.roadmap.domain.Keyword;
import wooteco.prolog.roadmap.domain.Quiz;
import wooteco.prolog.roadmap.domain.repository.CurriculumRepository;
import wooteco.prolog.roadmap.domain.repository.KeywordRepository;
import wooteco.prolog.roadmap.domain.repository.QuizRepository;
import wooteco.prolog.session.domain.Session;
import wooteco.prolog.session.domain.repository.SessionRepository;
import wooteco.support.utils.RepositoryTest;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RepositoryTest
class QuizRepositoryTest {

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private KeywordRepository keywordRepository;

    @Autowired
    private CurriculumRepository curriculumRepository;

    @Autowired
    private SessionRepository sessionRepository;

    private Curriculum 백엔드;

    private Session session_백엔드_레벨1;

    private Keyword 자바;
    private Keyword 깃;

    private Quiz 자바_질문1;
    private Quiz 자바_질문2;
    private Quiz 깃_질문1;

    @BeforeEach
    void setUp() {
        백엔드 = curriculumRepository.save(new Curriculum("백엔드"));

        session_백엔드_레벨1 = sessionRepository.save(new Session(백엔드.getId(), "백엔드Java 레벨1"));

        자바 = keywordRepository.save(
            new Keyword(null, "자바", "자바입니다", 1, 1, session_백엔드_레벨1.getId(), null, null));

        깃 = keywordRepository.save(
            new Keyword(null, "깃", "깃입니다", 2, 2, session_백엔드_레벨1.getId(), null, null));

        자바_질문1 = quizRepository.save(new Quiz(자바, "자바의 아버지는 제임스 고슬링일까요 ? 제이슨일까요 ?"));
        자바_질문2 = quizRepository.save(new Quiz(자바, "Stream 은 자바 몇 버전부터 지원했을까요?"));
        깃_질문1 = quizRepository.save(new Quiz(깃, "깃 레포지토리를 날려버리면 복구가 가능할까요?"));
    }


    @DisplayName("키워드 id 로 퀴즈 List 를 조회한다.")
    @Test
    void findQuizzesByKeyword() {
        //given
        final List<Quiz> expect = Arrays.asList(자바_질문1, 자바_질문2);
        final List<Quiz> actual = quizRepository.findFetchQuizByKeywordId(자바.getId());

        assertThat(actual).containsExactlyElementsOf(expect);
    }

    @DisplayName("커리큘럼 id 로 퀴즈 List 를 조회한다.")
    @Test
    void findQuizzesByCurriculum() {
        // given
        Long 백엔드_커리큘럼_Id = 백엔드.getId();

        // when
        List<CurriculumQuizResponse> acutal = quizRepository.findQuizzesByCurriculum(백엔드_커리큘럼_Id);

        // then
        assertThat(acutal).hasSize(3);
    }
}
