package wooteco.prolog.studylog.infrastructure;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import wooteco.prolog.studylog.exception.JsonParseFailedException;
import wooteco.prolog.studylog.infrastructure.dto.OverallHealthDto;

@Profile({"elastic", "dev", "prod"})
@Component
public class HealthCheckClient {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private final WebClient webClient;

    public HealthCheckClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public List<OverallHealthDto> healthCheck() {
        return healthOfOverall();
    }

    /**
     * 엘라스틱 서치에 대한 전반적인 상태를 응답합니다.
     * GET _cat/health?format=json
     * @return
     */
    public List<OverallHealthDto> healthOfOverall() {
        Mono<String> body = webClient.get()
            .uri(uriBuilder -> uriBuilder
                .path("/_cat/health")
                .queryParam("format", "json")
                .build()
            ).retrieve()
            .onStatus(HttpStatus::is4xxClientError, clientResponse -> Mono.error(RuntimeException::new))
            .onStatus(HttpStatus::is5xxServerError, clientResponse -> Mono.error(RuntimeException::new))
            .bodyToMono(String.class);

        String responseArray = body.block();
        try {
            return objectMapper.readValue(responseArray, new TypeReference<List<OverallHealthDto>>() {});
        } catch (JsonProcessingException e) {
            throw new JsonParseFailedException();
        }
    }

    public void healthOfIndex() {
        // 인덱스 상태
        // GET _cat/indices/studylog-document?format=json
    }

    public void healthOfCluster() {

        // 클러스터 상태
        // GET _cluster/health?format=json
    }


}
