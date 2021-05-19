package wooteco.studylog.post.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wooteco.studylog.post.web.controller.dto.AuthorResponse;
import wooteco.studylog.post.web.controller.dto.CategoryResponse;
import wooteco.studylog.post.web.controller.dto.PostRequest;
import wooteco.studylog.post.web.controller.dto.PostResponse;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    @GetMapping
    public ResponseEntity<List<PostResponse>> showAll() {
        List<PostResponse> postResponse = Collections.singletonList(
                new PostResponse(1L,
                        new AuthorResponse(1L, "뽀모", "image"),
                        LocalDateTime.now(),
                        new CategoryResponse(1L, "미션1"),
                        "제목",
                        "내용",
                        Arrays.asList("자바", "쟈스")
                )
        );
        return ResponseEntity.ok(postResponse);
    }

    @PostMapping
    public ResponseEntity<PostResponse> createPost(@RequestBody List<PostRequest> postRequest) {
        PostResponse postResponse = new PostResponse(1L,
                new AuthorResponse(1L, "뽀모", "image"),
                LocalDateTime.now(),
                new CategoryResponse(1L, "미션1"),
                "제목",
                "내용",
                Arrays.asList("자바", "쟈스"));

        return ResponseEntity.created(URI.create("/logs/" + postResponse.getId())).body(postResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> showPost(@PathVariable Long id){
        PostResponse postResponse =
                new PostResponse(1L,
                        new AuthorResponse(1L, "웨지", "image"),
                        LocalDateTime.now(),
                        new CategoryResponse(1L, "미션1"),
                        "매감",
                        "매서운감자",
                        Arrays.asList("자바", "자스")

                );
        return ResponseEntity.ok(postResponse);
    }

    @GetMapping("/categories")
    public ResponseEntity<List<CategoryResponse>> showCategories() {
        List<CategoryResponse> categoryResponses = Arrays.asList(
                new CategoryResponse(1L, "빈지모"),
                new CategoryResponse(2L, "빈포모"),
                new CategoryResponse(3L, "웨지노")
        );

        return ResponseEntity.ok(categoryResponses);
    }
}