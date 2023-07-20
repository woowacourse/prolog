package wooteco.prolog.roadmap.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.member.domain.Role;
import wooteco.prolog.roadmap.domain.EssayAnswer;
import wooteco.prolog.roadmap.domain.Keyword;
import wooteco.prolog.roadmap.domain.Quiz;
import wooteco.prolog.roadmap.domain.repository.EssayAnswerRepository;
import wooteco.support.utils.RepositoryTest;

@RepositoryTest
class EssayAnswerRepositoryTest {

    @Autowired
    private EssayAnswerRepository essayAnswerRepository;

    @DisplayName("퀴즈에 대한 서술형 답변을 저장한다.")
    @Test
    void save() {
        Keyword keyword = Keyword.createKeyword("자바", "자바 설명", 1, 5, 1L, null);
        Quiz quiz = new Quiz(1L, keyword, "자바 언어의 특징은?");
        Member member = new Member(11L, "username", "nickname", Role.CREW, 101L, "https://");
        EssayAnswer essayAnswer = new EssayAnswer(quiz, "객체 지향 언어", member);

        essayAnswerRepository.save(essayAnswer);

        assertThat(essayAnswer.getId()).isNotNull();
    }
}
