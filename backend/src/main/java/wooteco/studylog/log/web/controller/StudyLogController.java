package wooteco.studylog.log.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wooteco.studylog.log.service.StudyLogService;
import wooteco.studylog.log.web.controller.dto.*;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/logs")
public class StudyLogController {
    private final StudyLogService studyLogService;

    public StudyLogController(StudyLogService studyLogService) {
        this.studyLogService = studyLogService;
    }

    @GetMapping
    public ResponseEntity<List<LogResponse>> showAll() {
        List<LogResponse> logResponses = Collections.singletonList(
                new LogResponse(1L,
                        new AuthorResponse(1L, "뽀모", "image"),
                        LocalDateTime.now(),
                        new CategoryResponse(1L, "미션1"),
                        "제목",
                        "내용",
                        Arrays.asList("자바", "쟈스")
                )
        );
        return ResponseEntity.ok(logResponses);
//        return ResponseEntity.ok(studyLogService.findAll());
    }

    @PostMapping
    public ResponseEntity<LogResponse> createLog(@RequestBody List<LogRequest> logRequest) {
        LogResponse logResponse = new LogResponse(1L,
                new AuthorResponse(1L, "뽀모", "image"),
                LocalDateTime.now(),
                new CategoryResponse(1L, "미션1"),
                "제목",
                "내용",
                Arrays.asList("자바", "쟈스"));
        studyLogService.insertLogs(logRequest);
        return ResponseEntity.created(URI.create("/logs/" + logResponse.getId())).body(logResponse);
//        LogResponse logResponse = studyLogService.insertLog(
//                logRequest.getCategoryId(),
//                logRequest.getTitle(),
//                logRequest.getTags(),
//                logRequest.getContent());
//        return ResponseEntity.created(URI.create("/logs/" + logResponse.getLogId())).body(logResponse);
    }

    @GetMapping("/{logId}")
    public ResponseEntity<LogResponse> showLog(@PathVariable Long logId){
//        LogResponse logResponse = studyLogService.findById(logId);
        LogResponse logResponse =
                new LogResponse(1L,
                        new AuthorResponse(1L, "웨지", "image"),
                        LocalDateTime.now(),
                        new CategoryResponse(1L, "미션1"),
                        "매감",
                        "매서운감자",
                        Arrays.asList("자바", "자스")

                );
        return ResponseEntity.ok(logResponse);
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
