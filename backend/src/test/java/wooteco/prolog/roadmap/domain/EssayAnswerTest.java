package wooteco.prolog.roadmap.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import wooteco.prolog.common.exception.BadRequestException;
import wooteco.prolog.member.domain.Member;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class EssayAnswerTest {

    @ValueSource(strings = {"", "  "})
    @ParameterizedTest
    void update_메서드에_answer_가_비어_있으면_예외가_발생한다(String answer) {
        //given
        final Member member = new Member(1L, null, null, null, null, null);
        final EssayAnswer essayAnswer = new EssayAnswer(null, null, member);

        //expect
        assertThatThrownBy(() -> essayAnswer.update(answer, member))
            .isInstanceOf(BadRequestException.class)
            .hasMessage("답변은 공백일 수 없습니다.");
    }

    @Test
    void update_메서드에_member_가_다르면_예외가_발생한다() {
        //given
        final Member member = new Member(1L, null, null, null, null, null);
        final Member another = new Member(2L, null, null, null, null, null);
        final EssayAnswer essayAnswer = new EssayAnswer(null, null, member);

        //expect
        assertThatThrownBy(() -> essayAnswer.update("answer", another))
            .isInstanceOf(BadRequestException.class)
            .hasMessage("본인이 작성한 답변만 수정할 수 있습니다.");
    }

    @Test
    void update_메서드를_통해_answer_를_바꿀_수_있다() {
        //given
        final String changed = "changed";

        final Member member = new Member(1L, null, null, null, null, null);
        final EssayAnswer essayAnswer = new EssayAnswer(null, null, member);

        //when
        essayAnswer.update(changed, member);

        //then
        assertThat(essayAnswer.getAnswer()).isEqualTo(changed);
    }

}
