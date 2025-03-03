package wooteco.prolog.session.domain.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.member.domain.Role;
import wooteco.prolog.member.domain.repository.MemberRepository;
import wooteco.prolog.session.domain.AnswerFeedback;
import wooteco.prolog.session.domain.Mission;
import wooteco.prolog.session.domain.QnaFeedbackContents;
import wooteco.prolog.session.domain.QnaFeedbackRequest;
import wooteco.prolog.session.domain.Question;
import wooteco.prolog.session.domain.Session;
import wooteco.support.utils.RepositoryTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@RepositoryTest
class AnswerFeedbackRepositoryTest {

    private static final QnaFeedbackRequest QNA_FEEDBACK_REQUEST = new QnaFeedbackRequest("", "", "");
    private static final QnaFeedbackContents QNA_FEEDBACK_CONTENTS = new QnaFeedbackContents("", "", "", 1);

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private MissionRepository missionRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerFeedbackRepository answerFeedbackRepository;


    @DisplayName("가장 마지막에 추가된 피드백을 반환한다.")
    @Test
    void findRecentByMemberIdAndQuestionIds() {
        //given
        final var member = memberRepository.save(new Member("jaeyeonling", "jaeyeonling", Role.CREW, 1L, "imageUrl"));
        final var session = sessionRepository.save(new Session("테스트용 세션"));
        final var mission = missionRepository.save(new Mission("테스트용 미션", session));

        final var question = questionRepository.save(new Question("1번 질문입니다.", mission));
        final var question2 = questionRepository.save(new Question("2번 질문입니다.", mission));
        final var question3 = questionRepository.save(new Question("3번 질문입니다.", mission));

        final var answerFeedback_1_1 = answerFeedbackRepository.save(new AnswerFeedback(question, member.getId(), QNA_FEEDBACK_REQUEST, QNA_FEEDBACK_CONTENTS, true));
        final var answerFeedback_1_2 = answerFeedbackRepository.save(new AnswerFeedback(question, member.getId(), QNA_FEEDBACK_REQUEST, QNA_FEEDBACK_CONTENTS, true));
        final var answerFeedback_1_3 = answerFeedbackRepository.save(new AnswerFeedback(question, member.getId(), QNA_FEEDBACK_REQUEST, QNA_FEEDBACK_CONTENTS, false));
        final var answerFeedback_2_1 = answerFeedbackRepository.save(new AnswerFeedback(question2, member.getId(), QNA_FEEDBACK_REQUEST, QNA_FEEDBACK_CONTENTS, true));
        final var answerFeedback_3_1 = answerFeedbackRepository.save(new AnswerFeedback(question3, member.getId(), QNA_FEEDBACK_REQUEST, QNA_FEEDBACK_CONTENTS, true));

        final var expected = List.of(answerFeedback_1_2, answerFeedback_2_1, answerFeedback_3_1);

        // when
        final var answerFeedbacks = answerFeedbackRepository.findRecentByMemberIdAndQuestionIdsAndVisible(
            member.getId(),
            List.of(question.getId(), question2.getId(), question3.getId())
        );

        assertAll(
            () -> assertThat(answerFeedbacks).hasSize(3),
            () -> assertThat(answerFeedbacks).containsExactlyElementsOf(expected)
        );
    }
}
