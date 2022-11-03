package wooteco.prolog.roadmap.ui;

import java.net.URI;
import java.util.Arrays;
import java.util.HashSet;
import org.elasticsearch.common.collect.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wooteco.prolog.roadmap.application.dto.KeywordCreateRequest;
import wooteco.prolog.roadmap.application.dto.KeywordResponse;
import wooteco.prolog.roadmap.application.dto.KeywordUpdateRequest;
import wooteco.prolog.roadmap.application.dto.KeywordsResponse;

@RestController
@RequestMapping("/sessions")
public class KeywordController {

    @PostMapping("/{sessionId}/keywords")
    public ResponseEntity<Void> createKeyword(@PathVariable Long sessionId,
                                              @RequestBody KeywordCreateRequest createRequest) {
        return ResponseEntity.created(URI.create("/sessions/" + sessionId + "/keywords/" + 1L)).build();
    }

    @GetMapping("/{sessionId}/keywords/{keywordId}")
    public ResponseEntity<KeywordResponse> findKeyword(@PathVariable Long sessionId,
                                                       @PathVariable Long keywordId) {
        return ResponseEntity.ok(keywordResponse());
    }

    private KeywordResponse keywordResponse() {
        return new KeywordResponse(
                2L,
                "SRP",
                "단일 책임 원칙입니다.",
                1,
                4,
                1L,
                null
        );
    }

    @PutMapping("/{sessionId}/keywords/{keywordId}")
    public ResponseEntity<Void> updateKeyword(@PathVariable Long sessionId,
                                              @PathVariable Long keywordId,
                                              @RequestBody KeywordUpdateRequest updateRequest) {
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{sessionId}/keywords/{keywordId}")
    public ResponseEntity<Void> deleteKeyword(@PathVariable Long sessionId,
                                              @PathVariable Long keywordId) {
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{sessionId}/keywords")
    public ResponseEntity<KeywordsResponse> findSessionIncludeRootKeywords(@PathVariable Long sessionId) {
        return ResponseEntity.ok(keywordsResponse());
    }

    private KeywordsResponse keywordsResponse() {
        return new KeywordsResponse(List.of(
                new KeywordResponse(
                        1L,
                        "자바",
                        "자바입니다.",
                        1,
                        5,
                        null,
                        null
                ),
                new KeywordResponse(
                        2L,
                        "스프링",
                        "스프링입니다.",
                        1,
                        4,
                        null,
                        null
                )
        ));
    }

    @GetMapping("/{sessionId}/keywords/{keywordId}/children")
    public ResponseEntity<KeywordResponse> find(@PathVariable Long sessionId,
                                                @PathVariable Long keywordId) {
        return ResponseEntity.ok(keywordResponseJava());
    }

    private KeywordResponse keywordResponseJava() {
        return new KeywordResponse(
                1L,
                "자바",
                "자바입니다.",
                1,
                5,
                null,
                new HashSet<>(Arrays.asList(
                                new KeywordResponse(
                                        2L,
                                        "List",
                                        "자바의 자료구조인 List입니다.",
                                        1,
                                        3,
                                        1L,
                                        keywordResponseList()
                                ),
                                new KeywordResponse(
                                        3L,
                                        "Set",
                                        "자바의 자료구조인 Set입니다.",
                                        2,
                                        3,
                                        1L,
                                        keywordResponseSet()
                                )
                ))
        );
    }

    private HashSet<KeywordResponse> keywordResponseList() {
        return new HashSet<>(Arrays.asList(
                new KeywordResponse(
                        4L,
                        "ArrayList",
                        "자바 List의 구현체 ArrayList입니다.",
                        1,
                        2,
                        2L,
                        null
                ),
                new KeywordResponse(
                        5L,
                        "LinkedList",
                        "자바 List의 구현체 ArrayList입니다.",
                        2,
                        2,
                        2L,
                        null
                )
        ));
    }

    private HashSet<KeywordResponse> keywordResponseSet() {
        return new HashSet<>(Arrays.asList(
                new KeywordResponse(
                        6L,
                        "HashSet",
                        "자바 Set의 구현체 HashSet입니다.",
                        1,
                        1,
                        3L,
                        null
                ),
                new KeywordResponse(
                        7L,
                        "TreeSet",
                        "자바 Set의 구현체 TreeSet입니다.",
                        2,
                        1,
                        3L,
                        null
                )
        ));
    }
}
