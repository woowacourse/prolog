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
import wooteco.prolog.studylog.application.dto.ElasticHealthResponse;
import wooteco.prolog.studylog.exception.JsonParseFailedException;
import wooteco.prolog.studylog.infrastructure.dto.ClusterHealthDto;
import wooteco.prolog.studylog.infrastructure.dto.ClusterHealthDtos;
import wooteco.prolog.studylog.infrastructure.dto.IndexHealthDto;
import wooteco.prolog.studylog.infrastructure.dto.IndexHealthDtos;

@Profile({"elastic", "dev", "prod"})
@Component
public class HealthCheckClient {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private final WebClient webClient;

    public HealthCheckClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public ElasticHealthResponse healthCheck(String index) {
        return ElasticHealthResponse.of(healthOfCluster(), healthOfIndex(index));
    }

    public ClusterHealthDtos healthOfCluster() {
        try {
            return ClusterHealthDtos.from(
                objectMapper.readValue(retrieve("/_cat/health"),
                                       new TypeReference<List<ClusterHealthDto>>() {}
                ));
        } catch (JsonProcessingException e) {
            throw new JsonParseFailedException();
        }
    }

    public IndexHealthDtos healthOfIndex(String index) {
        try {
            return IndexHealthDtos.from(
                objectMapper.readValue(retrieve("/_cat/indices/" + index),
                                       new TypeReference<List<IndexHealthDto>>() {}
                ));
        } catch (JsonProcessingException e) {
            throw new JsonParseFailedException();
        }
    }

    private String retrieve(String uri) {
        // TODO 에러 처리
        return webClient.get()
            .uri(uriBuilder -> uriBuilder
                .path(uri)
                .queryParam("format", "json")
                .build()
            ).retrieve()
            .onStatus(HttpStatus::is4xxClientError, clientResponse -> Mono.error(RuntimeException::new))
            .onStatus(HttpStatus::is5xxServerError, clientResponse -> Mono.error(RuntimeException::new))
            .bodyToMono(String.class)
            .block();
    }

}
