package wooteco.prolog.interview.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import wooteco.prolog.interview.domain.FollowUpQuestion;
import wooteco.prolog.interview.domain.InterviewMessage;
import wooteco.prolog.interview.domain.InterviewMessages;
import wooteco.prolog.interview.domain.InterviewSession;

import java.util.List;

@Component
public final class InterviewSessionMapper {

    private final ObjectMapper objectMapper;

    public InterviewSessionMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public InterviewSessionResponse mapToResponse(final InterviewSession interviewSession) {
        return new InterviewSessionResponse(
            interviewSession.getId(),
            interviewSession.getMemberId(),
            interviewSession.isFinished(),
            mapToResponse(interviewSession.getMessages()),
            interviewSession.getRound()
        );
    }

    private List<InterviewMessageResponse> mapToResponse(final InterviewMessages messages) {
        return messages.filter(InterviewMessage::isNotSystem)
            .map(this::mapToResponse)
            .toList();
    }

    private InterviewMessageResponse mapToResponse(final InterviewMessage message) {
        if (message.isInterviewerFollowUp()) {
            final var followUpQuestion = toFollowUpQuestion(message);
            return new InterviewMessageResponse(
                message.getSender(),
                followUpQuestion.followUpQuestion(),
                followUpQuestion.hint(),
                message.getCreatedAt()
            );
        }

        return new InterviewMessageResponse(
            message.getSender(),
            message.getVisibleMessage(),
            "",
            message.getCreatedAt()
        );
    }

    private FollowUpQuestion toFollowUpQuestion(final InterviewMessage message) {
        try {
            return objectMapper.readValue(message.getVisibleMessage(), FollowUpQuestion.class);
        } catch (final JsonProcessingException e) {
            throw new IllegalArgumentException("잘못된 형식의 메시지입니다.");
        }
    }
}
