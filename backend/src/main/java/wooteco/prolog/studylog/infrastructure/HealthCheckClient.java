package wooteco.prolog.studylog.infrastructure;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import wooteco.prolog.studylog.infrastructure.dto.TestDto;

@Profile({"local", "dev", "prod"})
@Component
public class HealthCheckClient {

    @Value("${elasticsearch.host}")
    private String host;

    @Value("${elasticsearch.port}")
    private String port;

    public void healthCheck() {

        // 전체 상태
        // GET _cat/health?format=json
        // 인덱스 상태
        // GET _cat/indices/studylog-document?format=json
        // 클러스터 상태
        // GET _cluster/health?format=json

        HttpClient httpClient = HttpClient.create()
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
            .responseTimeout(Duration.ofMillis(5000))
            .doOnConnected(conn -> {
                conn.addHandlerLast(new ReadTimeoutHandler(5000, TimeUnit.MILLISECONDS))
                    .addHandlerLast(new WriteTimeoutHandler(5000, TimeUnit.MILLISECONDS));
            });

        WebClient webClient = WebClient.builder()
            .clientConnector(new ReactorClientHttpConnector(httpClient))
            .baseUrl("http://127.0.0.1:9200")
            .build();

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
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            List<TestDto> testDtos
                = objectMapper.readValue(responseArray, new TypeReference<List<TestDto>>() {
            });
        } catch (JsonProcessingException e) {

        }
        System.out.println();

    }
}
