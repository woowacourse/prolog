package wooteco.prolog.interview.infrastructure;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import wooteco.prolog.common.exception.AiResponseProcessingException;
import wooteco.prolog.interview.domain.FollowUpQuestion;
import wooteco.prolog.interview.domain.InterviewMessage;
import wooteco.prolog.interview.domain.InterviewMessages;
import wooteco.prolog.interview.domain.Interviewer;

import java.util.List;

@Profile({"local", "test"})
@Component
public final class FakeInterviewer implements Interviewer {

    private final String interviewerMessage;

    FakeInterviewer(final ObjectMapper objectMapper) {
        try {
            interviewerMessage = objectMapper.writeValueAsString(
                new FollowUpQuestion("추가 질문", "힌트")
            );
        } catch (final JsonProcessingException e) {
            throw new AiResponseProcessingException(e);
        }
    }

    @Override
    public InterviewMessages start(
        final String goal,
        final String question
    ) {
        return new InterviewMessages(List.of(
            InterviewMessage.ofSystemGuide("시스템 가이드라인"),
            InterviewMessage.ofInitialQuestion("초기 질문", "초기 질문")
        ));
    }

    @Override
    public InterviewMessages followUp(
        final InterviewMessages interviewMessages,
        final String answer
    ) {
        return interviewMessages.with(InterviewMessage.ofInterviewee(answer, answer))
            .with(InterviewMessage.ofInterviewer(interviewerMessage));
    }

    @Override
    public InterviewMessages finish(final InterviewMessages interviewMessages) {
        return interviewMessages.with(InterviewMessage.ofClosingSummaryRequest("리포트 생성좀"))
            .with(InterviewMessage.ofClosingSummaryResponse("리포트"));
    }
}
