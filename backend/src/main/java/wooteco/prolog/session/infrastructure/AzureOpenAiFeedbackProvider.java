package wooteco.prolog.session.infrastructure;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.azure.openai.AzureOpenAiChatModel;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.MessageType;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import wooteco.prolog.session.domain.QnaFeedbackContents;
import wooteco.prolog.session.domain.QnaFeedbackProvider;
import wooteco.prolog.session.domain.QnaFeedbackRequest;

import java.util.List;
import java.util.Map;

@Profile({"prod", "dev"})
@Component
public final class AzureOpenAiFeedbackProvider implements QnaFeedbackProvider {

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

    private final AzureOpenAiChatModel chatModel;

    private final ObjectMapper objectMapper;

    private final PromptCreator feedbackPromptCreator;

    AzureOpenAiFeedbackProvider(
        final AzureOpenAiChatModel chatModel,
        final ObjectMapper objectMapper,
        @Value("classpath:prompts/qna-feedback.st") final Resource resource
    ) {
        this.chatModel = chatModel;
        this.objectMapper = objectMapper;
        this.feedbackPromptCreator = new PromptCreator(resource);
    }

    @Override
    public QnaFeedbackContents evaluate(final QnaFeedbackRequest request) {
        final var systemMessage = feedbackPromptCreator.createMessageForSystem(Map.of(
            "goal", request.goal(),
            "question", request.question(),
            "responseFormat", RESPONSE_FORMAT
        ));

        final var userMessage = new UserMessage(request.answer());
        final var prompt = new Prompt(List.of(
            systemMessage,
            userMessage
        ));

        final var response = chatModel.call(prompt);
        final var responseText = response.getResult().getOutput().getText();

        try {
            return objectMapper.readValue(responseText, QnaFeedbackContents.class);
        } catch (final JsonProcessingException e) {
            log.error("Failed to parse response from chat model [responseText={}]", responseText, e);
            throw new RuntimeException("Invalid response format from AI model", e);
        }
    }


    private final class PromptCreator {

        private final PromptTemplate template;

        public PromptCreator(final Resource resource) {
            this.template = new PromptTemplate(resource);
        }

        public Message createMessageForSystem(final Map<String, Object> model) {
            return new SystemMessageWrapper(template.createMessage(model));
        }

        // Note: promptTemplate으로 만들 경우 role을 지정할 수 없어 SystemMessage를 포장한다.
        private record SystemMessageWrapper(Message message) implements Message {
            @Override
            public MessageType getMessageType() {
                return MessageType.SYSTEM;
            }

            @Override
            public String getText() {
                return message.getText();
            }

            @Override
            public Map<String, Object> getMetadata() {
                return message.getMetadata();
            }
        }
    }
}
