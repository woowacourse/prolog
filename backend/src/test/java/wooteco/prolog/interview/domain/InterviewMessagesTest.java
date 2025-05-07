package wooteco.prolog.interview.domain;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class InterviewMessagesTest {

    @Test
    void 인터뷰이의_답변_갯수를_조회한다() {
        InterviewMessages interviewMessages = new InterviewMessages(List.of(
            InterviewMessage.ofInterviewee("질문2", "질문2")
        ));

        assertThat(interviewMessages.getIntervieweeMessageCount()).isEqualTo(1);
    }

    @Test
    void 인터뷰이의_답변이_3개다() {
        InterviewMessages interviewMessages = new InterviewMessages(List.of(
            InterviewMessage.ofInterviewee("질문1", "질문1"),
            InterviewMessage.ofInterviewee("질문2", "질문2"),
            InterviewMessage.ofInterviewee("질문3", "질문3")
        ));

        assertThat(interviewMessages.getIntervieweeMessageCount()).isEqualTo(3);
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


    @Test
    void 빈_메시지_리스트로_생성시_인터뷰이_답변수는_0이고_종료할수_없다() {
        InterviewMessages interviewMessages = new InterviewMessages(List.of());

        assertAll(
            () -> assertThat(interviewMessages.getMessages()).isEmpty(),
            () -> assertThat(interviewMessages.getIntervieweeMessageCount()).isZero(),
            () -> assertThat(interviewMessages.canFinish()).isFalse()
        );
    }

    @Test
    void 종료조건_인터뷰이_답변_부족시_종료불가() {
        InterviewMessages interviewMessages = new InterviewMessages(List.of(
            InterviewMessage.ofSystemGuide("시스템 가이드라인")
        ));
        for (int i = 0; i < 9; i++) { // 9개의 질문-답변 쌍
            interviewMessages = interviewMessages.with(InterviewMessage.ofInterviewer("질문 " + (i + 1)))
                .with(InterviewMessage.ofInterviewee("답변 " + (i + 1), "답변 " + (i + 1)));
            assertThat(interviewMessages.canFinish()).isFalse();
        }
        // 마지막 인터뷰어 질문만 추가 (총 9개의 답변, 10개의 질문)
        interviewMessages = interviewMessages.with(InterviewMessage.ofInterviewer("질문 10"));
        assertThat(interviewMessages.canFinish()).isFalse();
        assertThat(interviewMessages.getIntervieweeMessageCount()).isEqualTo(9);
    }

    @Test
    void 종료조건_마지막이_인터뷰어_질문이면_종료불가() {
        InterviewMessages interviewMessages = new InterviewMessages(List.of(
            InterviewMessage.ofSystemGuide("시스템 가이드라인")
        ));
        for (int i = 0; i < 10; i++) {
            interviewMessages = interviewMessages.with(InterviewMessage.ofInterviewer("질문 " + (i + 1)))
                .with(InterviewMessage.ofInterviewee("답변 " + (i + 1), "답변 " + (i + 1)));
        }
        // 인터뷰이 답변으로 끝났으므로 종료 가능
        assertThat(interviewMessages.canFinish()).isTrue();

        // 인터뷰어 질문 추가
        interviewMessages = interviewMessages.with(InterviewMessage.ofInterviewer("추가 질문"));
        assertThat(interviewMessages.canFinish()).isFalse(); // 인터뷰어의 질문으로 끝나면 종료 불가
        assertThat(interviewMessages.getIntervieweeMessageCount()).isEqualTo(10);
    }

    @Test
    void 인터뷰이_답변_개수_정확히_계산_확인() {
        InterviewMessages interviewMessages = new InterviewMessages(List.of(
            InterviewMessage.ofSystemGuide("가이드1"),
            InterviewMessage.ofInterviewer("질문1"),
            InterviewMessage.ofInterviewee("답변1", "답변1"), // 1
            InterviewMessage.ofSystemGuide("가이드2"),
            InterviewMessage.ofInterviewer("질문2"),
            InterviewMessage.ofInterviewee("답변2", "답변2"), // 2
            InterviewMessage.ofInterviewer("질문3"),
            InterviewMessage.ofInterviewee("답변3", "답변3")  // 3
        ));
        assertThat(interviewMessages.getIntervieweeMessageCount()).isEqualTo(3);
    }

    @Test
    void 인터뷰이_메시지_없을때_답변수_0_반환() {
        InterviewMessages interviewMessages = new InterviewMessages(List.of(
            InterviewMessage.ofSystemGuide("가이드1"),
            InterviewMessage.ofInterviewer("질문1"),
            InterviewMessage.ofSystemGuide("가이드2"),
            InterviewMessage.ofInterviewer("질문2")
        ));
        assertThat(interviewMessages.getIntervieweeMessageCount()).isZero();
    }
}
