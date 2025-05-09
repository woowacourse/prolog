package wooteco.prolog.interview.infrastructure;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.azure.openai.AzureOpenAiChatModel;
import org.springframework.ai.azure.openai.AzureOpenAiChatOptions;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import wooteco.prolog.common.exception.AiResponseProcessingException;
import wooteco.prolog.interview.domain.FollowUpQuestion;
import wooteco.prolog.interview.domain.InterviewMessage;
import wooteco.prolog.interview.domain.InterviewMessages;
import wooteco.prolog.interview.domain.Interviewer;
import wooteco.prolog.session.domain.QnaFeedbackRequest;

import java.util.List;
import java.util.Map;

@Profile({"prod", "dev"})
@Component
public class AzureOpenAiInterviewer implements Interviewer {

    private static final Logger log = LoggerFactory.getLogger(AzureOpenAiInterviewer.class);

    // Note: 스크립트 파일에 {}와 같은 특수문자는 들어갈 수 없어서 별도 템플릿으로 만든다.
    private static final String RESPONSE_FORMAT = """
        {
            "followUpQuestion": "String (추가 질문)",
            "hint": "String (가이드라인)"
        }
        """;
    private static final String RESPONSE_EXAMPLE = """
        {
            "followUpQuestion": "이 원칙을 적용하지 않았을 때 발생할 수 있는 문제는 무엇인가요?",
            "hint": "만약 대규모 트래픽 환경이라면, 이 개념이 어떻게 달라질까요?"
        }
        """;

    private final int totalQuestionCount;

    private final PromptTemplate systemGuidePrompt;

    private final PromptTemplate initialQuestionPrompt;

    private final PromptTemplate followUpPromptTemplate;

    private final PromptTemplate finishPromptTemplate;

    private final ObjectMapper objectMapper;

    private final AzureOpenAiChatModel chatModel;

    public AzureOpenAiInterviewer(
        @Value("${interview.total-question-count}") final int totalQuestionCount,
        @Value("classpath:prompts/qna-interview-system-guide.st") final Resource systemGuideResource,
        @Value("classpath:prompts/qna-interview-initial-question.st") final Resource initialQuestionResource,
        @Value("classpath:prompts/qna-interview-interactive-follow-up.st") final Resource followUpResource,
        @Value("classpath:prompts/qna-interview-closing-summary.st") final Resource closingSummaryResource,
        final ObjectMapper objectMapper,
        final AzureOpenAiChatModel chatModel
    ) {
        this.totalQuestionCount = totalQuestionCount;
        this.systemGuidePrompt = new PromptTemplate(systemGuideResource);
        this.initialQuestionPrompt = new PromptTemplate(initialQuestionResource);
        this.followUpPromptTemplate = new PromptTemplate(followUpResource);
        this.finishPromptTemplate = new PromptTemplate(closingSummaryResource);
        this.objectMapper = objectMapper;
        this.chatModel = chatModel;
    }

    @Override
    public InterviewMessages start(
        final String goal,
        final String question
    ) {
        log.debug("Start interview [goal={}, question={}]", goal, question);

        return new InterviewMessages(List.of(
            createSystemMessage(goal),
            createFirstMessage(question)
        ));
    }

    public InterviewMessage createSystemMessage(final String goal) {
        final var message = systemGuidePrompt.createMessage(Map.of(
            "goal", goal,
            "totalQuestionCount", totalQuestionCount
        ));
        return InterviewMessage.ofSystemGuide(message.getText());
    }

    public InterviewMessage createFirstMessage(final String question) {
        final var message = initialQuestionPrompt.createMessage(Map.of(
            "question", question,
            "totalQuestionCount", totalQuestionCount
        ));
        return InterviewMessage.ofInitialQuestion(question, message.getText());
    }

    @Retryable(
        retryFor = AiResponseProcessingException.class,
        backoff = @Backoff(delay = 1000),
        maxAttempts = 4,
        recover = "logging"
    )
    @Override
    public InterviewMessages followUp(
        final InterviewMessages interviewMessages,
        final String answer
    ) {
        if (interviewMessages.getCurrentRound() > totalQuestionCount) {
            throw new IllegalStateException("인터뷰가 종료되었습니다.");
        }

        log.debug("Follow up [interviewMessages={}, answer={}]", interviewMessages, answer);

        final var messagesWithUserAnswer = interviewMessages.with(createUserMessage(interviewMessages.getCurrentRound(), answer));

        final var rawFollowUpQuestion = askToInterviewer(messagesWithUserAnswer);
        checkFollowUpQuestion(rawFollowUpQuestion); // Validates AI response format

        // Add AI's new message (the follow-up question/statement) to the history.
        return messagesWithUserAnswer.with(InterviewMessage.ofInterviewer(rawFollowUpQuestion));
    }

    private void checkFollowUpQuestion(final String rawFollowUpQuestion) {
        try {
            objectMapper.readValue(rawFollowUpQuestion, FollowUpQuestion.class);
        } catch (final JsonProcessingException e) {
            log.warn("Failed to parse follow up question [rawFollowUpQuestion={}]", rawFollowUpQuestion, e);
            throw new AiResponseProcessingException(e);
        }
    }

    @Recover
    void logging(final RuntimeException e, final QnaFeedbackRequest request) {
        log.error("Failed to follow up [request={}]", request, e);
    }

    private InterviewMessage createUserMessage(
        final int round,
        final String answer
    ) {
        final var message = followUpPromptTemplate.createMessage(Map.of(
            "round", round,
            "answer", answer,
            "responseFormat", RESPONSE_FORMAT,
            "responseExample", RESPONSE_EXAMPLE
        ));
        return InterviewMessage.ofInterviewee(answer, message.getText());
    }

    @Override
    public InterviewMessages finish(final InterviewMessages interviewMessages) {
        if (interviewMessages.getCurrentRound() < totalQuestionCount) {
            throw new IllegalStateException("인터뷰가 종료되지 않았습니다.");
        }

        log.debug("Finish [interviewMessages={}]", interviewMessages);
        final var messages = interviewMessages.with(createFinalMessage());
        final var answer = askToInterviewer(messages);
        return messages.with(InterviewMessage.ofClosingSummaryResponse(answer));
    }

    private InterviewMessage createFinalMessage() {
        final var message = finishPromptTemplate.createMessage();
        return InterviewMessage.ofClosingSummaryRequest(message.getText());
    }

    private String askToInterviewer(final InterviewMessages interviewMessages) {
        final var response = chatModel.call(toPrompt(interviewMessages));
        return response.getResult().getOutput().getText();
    }

    private Prompt toPrompt(final InterviewMessages interviewMessages) {
        return toPrompt(interviewMessages.values());
    }

    private Prompt toPrompt(final List<InterviewMessage> interviewMessages) {
        AzureOpenAiChatOptions chatOptions = new AzureOpenAiChatOptions();
        chatOptions.setTemperature(1.0);
        return new Prompt(toMessages(interviewMessages), chatOptions);
    }

    private List<Message> toMessages(final List<InterviewMessage> interviewMessages) {
        return interviewMessages.stream()
            .map(this::toMessage)
            .toList();
    }

    private Message toMessage(final InterviewMessage interviewMessage) {
        return switch (interviewMessage.getSender()) {
            case SYSTEM, INTERVIEWER -> new AssistantMessage(interviewMessage.getFormattedContent());
            case INTERVIEWEE -> new UserMessage(interviewMessage.getFormattedContent());
        };
    }
}
