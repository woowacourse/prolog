package wooteco.prolog.interview.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Embeddable
public class InterviewMessage {

    @Enumerated(EnumType.STRING)
    private Sender sender;

    @Enumerated(EnumType.STRING)
    private Type type;

    @Column(nullable = false, length = 8000)
    private String originalContent;

    @Column(nullable = false, length = 8000)
    private String formattedContent;

    @Column(nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime createdAt;

    protected InterviewMessage() {
    }

    public InterviewMessage(
        final Sender sender,
        final Type type,
        final String originalContent,
        final String formattedContent
    ) {
        this(sender, type, originalContent, formattedContent, LocalDateTime.now());
    }

    public InterviewMessage(
        final Sender sender,
        final Type type,
        final String originalContent,
        final String formattedContent,
        final LocalDateTime createdAt
    ) {
        this.sender = sender;
        this.type = type;
        this.originalContent = originalContent;
        this.formattedContent = formattedContent;
        this.createdAt = createdAt;
    }

    public static InterviewMessage ofSystemGuide(final String formattedContent) {
        return new InterviewMessage(Sender.SYSTEM, Type.SYSTEM_GUIDE, "", formattedContent);
    }

    public static InterviewMessage ofInitialQuestion(
        final String originalContent,
        final String formattedContent
    ) {
        return new InterviewMessage(Sender.INTERVIEWER, Type.INITIAL_QUESTION, originalContent, formattedContent);
    }

    public static InterviewMessage ofInterviewer(final String formattedContent) {
        return new InterviewMessage(Sender.INTERVIEWER, Type.FOLLOW_UP, "", formattedContent);
    }

    public static InterviewMessage ofInterviewee(final String originalContent, final String formattedContent) {
        return new InterviewMessage(Sender.INTERVIEWEE, Type.FOLLOW_UP, originalContent, formattedContent);
    }

    public static InterviewMessage ofClosingSummaryRequest(final String formattedContent) {
        return new InterviewMessage(Sender.SYSTEM, Type.CLOSING_SUMMARY, "", formattedContent);
    }

    public static InterviewMessage ofClosingSummaryResponse(final String formattedContent) {
        return new InterviewMessage(Sender.INTERVIEWER, Type.CLOSING_SUMMARY, "", formattedContent);
    }

    public boolean isNotSystem() {
        return sender != Sender.SYSTEM;
    }

    public boolean isByInterviewer() {
        return sender == Sender.INTERVIEWER;
    }

    public boolean isByInterviewee() {
        return sender == Sender.INTERVIEWEE;
    }

    public Sender getSender() {
        return sender;
    }

    public String getFormattedContent() {
        return formattedContent;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public boolean isSystemClosingSummary() {
        return sender == Sender.SYSTEM && type == Type.CLOSING_SUMMARY;
    }

    public String getVisibleMessage() {
        if (isInitialQuestion()) {
            return originalContent;
        }
        if (isInterviewerFollowUp()) {
            return formattedContent;
        }
        if (isIntervieweeFollowUp()) {
            return originalContent;
        }
        if (isInterviewerClosingSummary()) {
            return formattedContent;
        }

        throw new IllegalStateException("보여질 수 없는 메시지입니다.");
    }

    public boolean isInterviewerClosingSummary() {
        return sender == Sender.INTERVIEWER && type == Type.CLOSING_SUMMARY;
    }

    private boolean isInitialQuestion() {
        return sender == Sender.INTERVIEWER && type == Type.INITIAL_QUESTION;
    }

    public boolean isInterviewerFollowUp() {
        return sender == Sender.INTERVIEWER && type == Type.FOLLOW_UP;
    }

    public boolean isIntervieweeFollowUp() {
        return sender == Sender.INTERVIEWEE && type == Type.FOLLOW_UP;
    }

    @Override
    public String toString() {
        return "InterviewMessage{" +
            "sender=" + sender +
            ", type=" + type +
            ", originalContent='" + originalContent + '\'' +
            ", formattedContent='" + formattedContent + '\'' +
            ", createdAt=" + createdAt +
            '}';
    }

    public enum Sender {
        SYSTEM,
        INTERVIEWER,
        INTERVIEWEE
    }

    public enum Type {
        SYSTEM_GUIDE,
        INITIAL_QUESTION,
        FOLLOW_UP,
        CLOSING_SUMMARY
    }
}
