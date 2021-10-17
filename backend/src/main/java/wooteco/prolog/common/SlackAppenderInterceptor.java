package wooteco.prolog.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
public class SlackAppenderInterceptor implements HandlerInterceptor {

    private static final String slackWebHookUrl = System.getenv("SLACK_LOGGER_WEBHOOK_URI");
    private static final WebClient webClient = WebClient.create(slackWebHookUrl);

    private final ObjectMapper objectMapper;
    private final SlackMessageGenerator slackMessageGenerator;

    public SlackAppenderInterceptor(ObjectMapper objectMapper,
                                    SlackMessageGenerator slackMessageGenerator) {
        this.objectMapper = objectMapper;
        this.slackMessageGenerator = slackMessageGenerator;
    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response,
                                Object handler,
                                Exception ex
    ) throws Exception {
        slackMessageGenerator.generate(request, response)
            .ifPresent(message -> send(toJson(message)));
    }

    private void send(String message) {
        webClient
            .post()
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(message)
            .retrieve()
            .bodyToMono(String.class)
            .block();
    }

    public String toJson(String message) {
        try {
            Map<String, String> values = new HashMap<>();
            values.put("text", message);

            return objectMapper.writeValueAsString(values);
        } catch(JsonProcessingException ignored){}
        return "{\"text\" : \"슬랙으로 보낼 데이터를 제이슨으로 변경하는데 에러가 발생함.\"}";
    }
}
