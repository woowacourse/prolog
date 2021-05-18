package wooteco.studylog.post.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wooteco.studylog.post.service.PostService;
import wooteco.studylog.post.web.controller.dto.*;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

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
//        return ResponseEntity.ok(studyLogService.findAll());
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
        postService.insertLogs(postRequest);
        return ResponseEntity.created(URI.create("/logs/" + postResponse.getId())).body(postResponse);
//        LogResponse logResponse = studyLogService.insertLog(
//                logRequest.getCategoryId(),
//                logRequest.getTitle(),
//                logRequest.getTags(),
//                logRequest.getContent());
//        return ResponseEntity.created(URI.create("/logs/" + logResponse.getLogId())).body(logResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> showPost(@PathVariable Long id){
//        LogResponse logResponse = studyLogService.findById(logId);
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
//        CategoryResponses allCategories = studyLogService.findAllCategories();
        List<CategoryResponse> categoryResponses = Arrays.asList(
                new CategoryResponse(1L, "빈지모"),
                new CategoryResponse(2L, "빈포모"),
                new CategoryResponse(3L, "웨지노")
        );

        return ResponseEntity.ok(categoryResponses);
    }
}
