package wooteco.prolog.interview.application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import wooteco.prolog.common.exception.BadRequestCode;
import wooteco.prolog.common.exception.BadRequestException;
import wooteco.prolog.interview.domain.InterviewMessage;
import wooteco.prolog.login.application.dto.GithubProfileResponse;
import wooteco.prolog.member.application.MemberService;
import wooteco.prolog.session.application.MissionService;
import wooteco.prolog.session.application.QuestionService;
import wooteco.prolog.session.application.SessionService;
import wooteco.prolog.session.application.dto.MissionRequest;
import wooteco.prolog.session.application.dto.QuestionRequest;
import wooteco.prolog.session.application.dto.SessionRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class InterviewServiceTest {

    private InterviewService interviewService;

    private long memberId;

    private long questionId;

    @BeforeEach
    void setUp(
        @Autowired final MemberService memberService,
        @Autowired final InterviewService interviewService,
        @Autowired final SessionService sessionService,
        @Autowired final MissionService missionService,
        @Autowired final QuestionService questionService
    ) {
        this.interviewService = interviewService;

        memberId = memberService.createMember(new GithubProfileResponse("neo",
                "neo",
                "1",
                ""))
            .getId();

        final var session = sessionService.create(new SessionRequest("세션1"));
        final var mission = missionService.create(new MissionRequest("미션", session.getId()));
        final var question = questionService.create(new QuestionRequest(mission.getId(), "질문입니다."));

        questionId = question.getId();
    }

    @Test
    void 세션을_시작한다() {
        final var session = interviewService.createSession(memberId, new InterviewSessionRequest(questionId));

        assertAll(
            () -> assertThat(session.id()).isNotNull(),
            () -> assertThat(session.memberId()).isEqualTo(memberId),
            () -> assertThat(session.finished()).isFalse(),
            () -> assertThat(session.messages())
                .hasSize(1)
                .first()
                .satisfies(message -> {
                    assertThat(message.content()).isNotNull();
                    assertThat(message.hint()).isEmpty();
                    assertThat(message.createdAt()).isNotNull();
                    assertThat(message.sender()).isEqualTo(InterviewMessage.Sender.INTERVIEWER);
                })
        );
    }

    @Test
    void 답변을_하면_꼬리_질문이_온다() {
        final var session = interviewService.createSession(memberId, new InterviewSessionRequest(questionId));

        final var newSession = interviewService.answer(
            memberId,
            session.id(),
            new InterviewAnswerRequest("답변입니다.")
        );

        assertAll(
            () -> assertThat(newSession.finished()).isFalse(),
            () -> assertThat(newSession.messages()).hasSize(3),
            () -> assertThat(newSession.messages().get(0).sender()).isEqualTo(InterviewMessage.Sender.INTERVIEWER),
            () -> assertThat(newSession.messages().get(1).sender()).isEqualTo(InterviewMessage.Sender.INTERVIEWEE),
            () -> assertThat(newSession.messages().get(2).sender()).isEqualTo(InterviewMessage.Sender.INTERVIEWER)
        );
    }

    @Test
    void 열번의_질문을_모두_답변하면_인터뷰가_종료된다() {
        var session = interviewService.createSession(memberId, new InterviewSessionRequest(questionId));
        for (int i = 0; i < 10; i++) {
            session = interviewService.answer(
                memberId,
                session.id(),
                new InterviewAnswerRequest("답변입니다.")
            );
        }

        final var lastSession = session;
        assertAll(
            () -> assertThat(lastSession.finished()).isTrue(),
            () -> assertThat(lastSession.messages()).hasSize(22)
        );
    }

    @Test
    void 인터뷰가_종료됐을_경우_답변은_불가능하다() {
        var session = interviewService.createSession(memberId, new InterviewSessionRequest(questionId));
        for (int i = 0; i < 10; i++) {
            session = interviewService.answer(
                memberId,
                session.id(),
                new InterviewAnswerRequest("답변입니다.")
            );
        }

        final var lastSession = session;
        BadRequestException badRequestException = assertThrows(BadRequestException.class, () -> {
            interviewService.answer(
                memberId,
                lastSession.id(),
                new InterviewAnswerRequest("답변입니다.")
            );
        });
        assertThat(badRequestException.getCode()).isEqualTo(BadRequestCode.INTERVIEW_SESSION_FINISHED.getCode());
    }
}
