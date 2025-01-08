package wooteco.prolog.roadmap.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
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
        final EssayAnswer essayAnswer = createEssayAnswer(createQuizWithPK(1L));

        essayAnswerRepository.save(essayAnswer);

        assertThat(essayAnswer.getId()).isNotNull();
    }

    @Test
    @DisplayName("멤버가 쓴 전체 답변들을 가져온다.")
    void findAllByMemberId() {
        //given
        final int expectedCount = 10;
        final List<EssayAnswer> answers = Stream.iterate(0, i -> i + 1)
            .limit(expectedCount)
            .map(this::createQuizWithPK)
            .map(this::createEssayAnswer)
            .collect(Collectors.toList());
        essayAnswerRepository.saveAll(answers);

        //when
        final Set<EssayAnswer> essayAnswers = essayAnswerRepository.findAllByMemberId(11L);

        //then
        assertThat(essayAnswers).hasSize(expectedCount);
    }

    private EssayAnswer createEssayAnswer(final Quiz quiz) {
        Member member = new Member(11L, "username", "nickname", Role.CREW, 101L, "https://");
        return new EssayAnswer(quiz, "객체 지향 언어", member);
    }

    private Quiz createQuizWithPK(final long id) {
        Keyword keyword = Keyword.createKeyword("자바", "자바 설명", 1, 5, 1L, null);
        return new Quiz(id, keyword, "자바 연어의 특징은?");
    }
}
