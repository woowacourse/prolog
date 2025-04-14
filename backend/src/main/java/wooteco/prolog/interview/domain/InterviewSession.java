package wooteco.prolog.interview.domain;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import wooteco.prolog.common.entity.AuditingEntity;
import wooteco.prolog.common.exception.BadRequestCode;
import wooteco.prolog.common.exception.BadRequestException;
import wooteco.prolog.session.domain.Question;

@Entity
public class InterviewSession extends AuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long memberId;

    private boolean finished;

    @Embedded
    private InterviewMessages interviewMessages = new InterviewMessages();

    protected InterviewSession() {
    }

    public InterviewSession(final Long memberId) {
        this.memberId = memberId;
        this.finished = false;
    }

    public void start(
        final Interviewer interviewer,
        final Question question
    ) {
        if (!interviewMessages.isEmpty()) {
            throw new BadRequestException(BadRequestCode.INTERVIEW_SESSION_ALREADY_STARTED);
        }

        interviewMessages = interviewer.start(
            question.getMission().getGoal(),
            question.getContent()
        );
    }

    public void answer(
        final Interviewer interviewer,
        final Long memberId,
        final String message
    ) {
        if (!this.memberId.equals(memberId)) {
            throw new BadRequestException(BadRequestCode.MEMBER_NOT_ALLOWED);
        }
        if (finished) {
            throw new BadRequestException(BadRequestCode.INTERVIEW_SESSION_FINISHED);
        }
        if (interviewMessages.lastMessage().isByInterviewee()) {
            throw new BadRequestException(BadRequestCode.INTERVIEW_SESSION_NOT_YOUR_TURN);
        }

        interviewMessages = interviewer.followUp(interviewMessages, message);
        if (interviewMessages.canFinish()) {
            finished = true;
            interviewMessages = interviewer.finish(interviewMessages);
        }
    }

    public Long getId() {
        return id;
    }

    public Long getMemberId() {
        return memberId;
    }

    public boolean isFinished() {
        return finished;
    }

    public InterviewMessages getMessages() {
        return interviewMessages;
    }
}
