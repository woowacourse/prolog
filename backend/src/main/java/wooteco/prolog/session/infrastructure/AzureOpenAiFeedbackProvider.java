package wooteco.prolog.session.infrastructure;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.azure.openai.AzureOpenAiChatModel;
import org.springframework.ai.chat.messages.AssistantMessage;
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
import wooteco.prolog.session.domain.QnaFeedbackContents;
import wooteco.prolog.session.domain.QnaFeedbackProvider;
import wooteco.prolog.session.domain.QnaFeedbackRequest;

import java.util.List;
import java.util.Map;

@Profile({"prod", "dev"})
@Component
public class AzureOpenAiFeedbackProvider implements QnaFeedbackProvider {

    private static final Logger log = LoggerFactory.getLogger(AzureOpenAiFeedbackProvider.class);

    // Note: 스크립트 파일에 {}와 같은 특수문자는 들어갈 수 없어서 별도 템플릿으로 만든다.
    private static final String RESPONSE_FORMAT = """
        {
            "strengths": "String (잘한 점)",
            "improvementPoints": "String (개선이 필요한 점)",
            "additionalLearning": "String (추가 학습 방향)",
            "score": Int (1~10)
        }
        """;
    private static final String RESPONSE_EXAMPLE = """
        {
            "strengths": "JUnit5와 AssertJ를 활용한 테스트 작성 개념을 정확히 설명하셨네요! TDD의 핵심 원칙도 언급하셔서 기본 개념을 잘 이해하고 계신 것 같아요.",
            "improvementPoints": "다만, 미션에서 어떻게 적용했는지에 대한 설명이 부족한 것 같아요. 예제 코드가 포함되면 더욱 명확한 답변이 될 수 있을 것 같아요!",
            "additionalLearning": "TDD를 실제 프로젝트에서 적용한 사례를 포함하여 설명해 보시면 어떨까요? 간단한 테스트 코드 예제를 작성해 보면 더욱 깊이 있는 학습이 될 거예요!",
            "score": 8
        }
        """;

    private final AzureOpenAiChatModel chatModel;

    private final ObjectMapper objectMapper;

    private final PromptTemplate template;

    AzureOpenAiFeedbackProvider(
        final AzureOpenAiChatModel chatModel,
        final ObjectMapper objectMapper,
        @Value("classpath:prompts/qna-feedback.st") final Resource resource
    ) {
        this.chatModel = chatModel;
        this.objectMapper = objectMapper;
        this.template = new PromptTemplate(resource);
    }

    @Retryable(
        retryFor = AiResponseProcessingException.class,
        backoff = @Backoff(delay = 1000),
        maxAttempts = 4,
        recover = "logging"
    )
    @Override
    public QnaFeedbackContents evaluate(final QnaFeedbackRequest request) {
        log.debug("Requesting feedback evaluation [request={}]", request);
        final var templateMessage = template.createMessage(Map.of(
            "goal", request.goal(),
            "question", request.question(),
            "responseFormat", RESPONSE_FORMAT,
            "responseExample", RESPONSE_EXAMPLE
        ));
        final var assistantMessage = new AssistantMessage(
            templateMessage.getText(),
            templateMessage.getMetadata()
        );
        final var userMessage = new UserMessage(request.answer());
        final var prompt = new Prompt(List.of(
            assistantMessage,
            userMessage
        ));

        final var response = chatModel.call(prompt);
        final var responseText = response.getResult().getOutput().getText();
        log.debug("Received response from chat model [responseText={}]", responseText);

        try {
            return objectMapper.readValue(responseText, QnaFeedbackContents.class);
        } catch (final JsonProcessingException e) {
            log.error("Failed to parse response from chat model [responseText={}]", responseText, e);
            throw new AiResponseProcessingException(e);
        }
    }

    @Recover
    void logging(final RuntimeException e, final QnaFeedbackRequest request) {
        log.error("Failed to evaluate feedback [request={}]", request, e);
    }
}
