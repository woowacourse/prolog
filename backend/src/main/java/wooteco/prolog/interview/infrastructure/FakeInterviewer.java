package wooteco.prolog.interview.infrastructure;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import wooteco.prolog.interview.domain.InterviewMessage;
import wooteco.prolog.interview.domain.InterviewMessages;
import wooteco.prolog.interview.domain.Interviewer;

@Profile({"local", "test"})
@Component
public final class FakeInterviewer implements Interviewer {

    private static final String FIXED_RESPONSE_JSON =
        "{\"followUpQuestion\":\"추가 질문\",\"hint\":\"힌트\"}";

    private final ObjectMapper objectMapper;

    public FakeInterviewer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public InterviewMessages start(final String goal, final String question) {
        return new InterviewMessages(List.of(
            InterviewMessage.ofSystemGuide("시스템 가이드라인"),
            InterviewMessage.ofInitialQuestion(question, question)
        ));
    }

    @Override
    public InterviewMessages followUp(
        final InterviewMessages interviewMessages,
        final String answer
    ) {
        return interviewMessages
            .with(InterviewMessage.ofInterviewee(answer, answer))
            .with(InterviewMessage.ofInterviewer(FIXED_RESPONSE_JSON));
    }

    @Override
    public InterviewMessages finish(final InterviewMessages interviewMessages) {
        return interviewMessages
            .with(InterviewMessage.ofClosingSummaryRequest("리포트 생성좀"))
            .with(InterviewMessage.ofClosingSummaryResponse("리포트"));
    }
}
