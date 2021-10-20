package wooteco.prolog.studylog.infrastructure;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import reactor.core.publisher.Mono;
import wooteco.prolog.studylog.application.dto.ElasticHealthResponse;
import wooteco.prolog.studylog.exception.ElasticsearchConnectException;
import wooteco.prolog.studylog.exception.ElasticsearchInternalException;
import wooteco.prolog.studylog.exception.JsonParseFailedException;
import wooteco.prolog.studylog.infrastructure.dto.ClusterHealth;
import wooteco.prolog.studylog.application.dto.ClusterHealthResponses;
import wooteco.prolog.studylog.infrastructure.dto.IndexHealth;
import wooteco.prolog.studylog.application.dto.IndexHealthResponses;

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

    public ClusterHealthResponses healthOfCluster() {
        try {
            return ClusterHealthResponses.from(
                objectMapper.readValue(retrieve("/_cat/health"),
                                       new TypeReference<List<ClusterHealth>>() {}
                ));
        } catch (JsonProcessingException e) {
            throw new JsonParseFailedException();
        }
    }

    public IndexHealthResponses healthOfIndex(String index) {
        try {
            return IndexHealthResponses.from(
                objectMapper.readValue(retrieve("/_cat/indices/" + index),
                                       new TypeReference<List<IndexHealth>>() {}
                ));
        } catch (JsonProcessingException e) {
            throw new JsonParseFailedException();
        }
    }

    private String retrieve(String uri) {
        try {
            return webClient.get()
                .uri(uriBuilder -> uriBuilder
                    .path(uri)
                    .queryParam("format", "json")
                    .build()
                ).retrieve()
                .onStatus(HttpStatus::is5xxServerError,
                          clientResponse -> Mono.error(ElasticsearchInternalException::new))
                .bodyToMono(String.class)
                .block();
        } catch (WebClientRequestException e) {
            throw new ElasticsearchConnectException(uri);
        }
    }

}
