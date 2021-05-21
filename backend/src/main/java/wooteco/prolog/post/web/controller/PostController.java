package wooteco.prolog.post.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wooteco.prolog.category.web.controller.dto.CategoryResponse;
import wooteco.prolog.post.web.controller.dto.AuthorResponse;
import wooteco.prolog.post.web.controller.dto.PostRequest;
import wooteco.prolog.post.web.controller.dto.PostResponse;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    @PostMapping
    public ResponseEntity<PostResponse> createPost(@RequestBody List<PostRequest> postRequest) {
        return ResponseEntity.created(URI.create("/posts/1")).build();
    }

    @GetMapping
    public ResponseEntity<List<PostResponse>> showAll() {
        List<PostResponse> postResponse = Collections.singletonList(
                new PostResponse(1L,
                        new AuthorResponse(1L, "뽀모", "https://avatars.githubusercontent.com/u/34594339?v=4"),
                        LocalDateTime.now(),
                        new CategoryResponse(1L, "레벨1 - 지하철 미션"),
                        "[JS] Custom Validity",
                        "input pattern 을 이용해검증 시 안내 메세지가 '유효하지 않은 입력입니다' 라고 뜨는 것을 보완하기 위해 이용함.\n" +
                                "https://github.com/woowacourse/javascript-subway/pull/54/files#diff-0abdf7ea3b5e10249e14e579a55250b98b53ea05379f9a9b9226c694f09647f0R50-R62",
                        Arrays.asList("자바", "쟈스")
                )
        );
        return ResponseEntity.ok(postResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> showPost(@PathVariable Long id) {
        PostResponse postResponse =
                new PostResponse(
                        id,
                        new AuthorResponse(1L, "웨지", "https://avatars.githubusercontent.com/u/52682603?s=64&v=4"),
                        LocalDateTime.now(),
                        new CategoryResponse(1L, "레벨1 - 지하철 미션"),
                        "매감",
                        "매서운감자매서운감자매서운감자매서운감자매서운감자매서운감자매서운감자매서운감자매서운감자매서운감자매서운감자\n" +
                                "매서운감자매서운감자매서운감자매서운감자매서운감자매서운감자매서운감자매서운감자매서운감자매서운감자매서운감자",
                        Arrays.asList("자바", "자스")
                );
        return ResponseEntity.ok(postResponse);
    }
}