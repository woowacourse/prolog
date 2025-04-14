package wooteco.prolog.interview.domain;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class InterviewMessagesTest {

    @Test
    void 인터뷰이의_답변_갯수가_라운드다() {
        InterviewMessages interviewMessages = new InterviewMessages(List.of(
            InterviewMessage.ofInterviewee("질문2", "질문2")
        ));

        assertThat(interviewMessages.getRound()).isEqualTo(1);
    }

    @Test
    void 인터뷰이의_답변이_3개면_3라운드다() {
        InterviewMessages interviewMessages = new InterviewMessages(List.of(
            InterviewMessage.ofInterviewee("질문1", "질문1"),
            InterviewMessage.ofInterviewee("질문2", "질문2"),
            InterviewMessage.ofInterviewee("질문3", "질문3")
        ));

        assertThat(interviewMessages.getRound()).isEqualTo(3);
    }

    @Test
    void 총_10번의_질답을_하면_끝낼_수_있다() {
        var interviewMessages = new InterviewMessages(List.of(
            InterviewMessage.ofSystemGuide("시스템 가이드라인"),
            InterviewMessage.ofInterviewer("첫 질문")
        ));
        for (int i = 0; i < 9; i++) {
            interviewMessages = interviewMessages.with(InterviewMessage.ofInterviewee("답변입니다", "답변입니다"))
                .with(InterviewMessage.ofInterviewer("질문입니다"));

            assertThat(interviewMessages.canFinish()).isFalse();
        }

        interviewMessages = interviewMessages.with(InterviewMessage.ofInterviewee("답변입니다", "답변입니다"));
        assertThat(interviewMessages.canFinish()).isTrue();
    }
}
