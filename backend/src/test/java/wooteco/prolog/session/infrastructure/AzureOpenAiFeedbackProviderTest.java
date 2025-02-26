package wooteco.prolog.session.infrastructure;

import com.azure.ai.openai.OpenAIClientBuilder;
import com.azure.core.credential.AzureKeyCredential;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.ai.azure.openai.AzureOpenAiChatModel;
import org.springframework.ai.azure.openai.AzureOpenAiChatOptions;
import org.springframework.ai.azure.openai.AzureOpenAiResponseFormat;
import org.springframework.core.io.DefaultResourceLoader;
import wooteco.prolog.session.domain.QnaFeedbackRequest;

// Note: Azure GPT에 대해 수동으로 테스트할 경우 @Disabled를 주석하고 키 값을 추가하여 테스트하면 된다.
@Disabled
class AzureOpenAiFeedbackProviderTest {

    private AzureOpenAiFeedbackProvider feedbackProvider;

    @BeforeEach
    void setUp() {
        final var defaultResourceLoader = new DefaultResourceLoader();
        final var clientBuilder = new OpenAIClientBuilder()
            .endpoint("/* 여기! */")
            .credential(new AzureKeyCredential("/* 여기! */"));
        final var options = AzureOpenAiChatOptions.builder()
            .deploymentName("gpt-4o")
            .maxTokens(1024)
            .temperature(0.5)
            .topP(0.4)
            .N(1)
            .responseFormat(AzureOpenAiResponseFormat.JSON)
            .build();
        final var azureOpenAiChatModel = new AzureOpenAiChatModel(clientBuilder, options);

        this.feedbackProvider = new AzureOpenAiFeedbackProvider(
            azureOpenAiChatModel,
            new ObjectMapper(),
            defaultResourceLoader.getResource("classpath:/prompts/qna-feedback.st")
        );
    }

    @Test
    void evaluate() {
        final var evaluate = feedbackProvider.evaluate(new QnaFeedbackRequest(
            "코드 리뷰를 경험하고 문화와 중요성을 이해한다.",
            "우테코 환경에서 코드 리뷰의 주요 원칙은 무엇인가?",
            "상호 존중을 기반으로 더 나은 코드를 위해 의견을 주고 받는다."
        ));

        System.out.println(evaluate);
    }
}
