package wooteco.studylog.log.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wooteco.studylog.log.service.StudyLogService;
import wooteco.studylog.log.web.controller.dto.CategoryResponses;
import wooteco.studylog.log.web.controller.dto.LogRequest;
import wooteco.studylog.log.web.controller.dto.LogResponse;
import wooteco.studylog.log.web.controller.dto.LogResponses;

import java.net.URI;

@RestController
@RequestMapping("/logs")
public class StudyLogController {
    private final StudyLogService studyLogService;

    public StudyLogController(StudyLogService studyLogService) {
        this.studyLogService = studyLogService;
    }

    @GetMapping
    public ResponseEntity<LogResponses> showAll() {
        return ResponseEntity.ok(studyLogService.findAll());
    }

    @PostMapping
    public ResponseEntity<LogResponse> createLog(@RequestBody LogRequest logRequest) {
        LogResponse logResponse = studyLogService.insertLog(
                logRequest.getCategoryId(),
                logRequest.getTitle(),
                logRequest.getTags(),
                logRequest.getContent());
        return ResponseEntity.created(URI.create("/logs/" + logResponse.getLogId())).body(logResponse);
    }

    @GetMapping("/{logId}")
    public ResponseEntity<LogResponse> showLog(@PathVariable Long logId){
        LogResponse logResponse = studyLogService.findById(logId);
        return ResponseEntity.ok(logResponse);
    }

    @GetMapping("/categories")
    public ResponseEntity<CategoryResponses> showCategories() {
        CategoryResponses allCategories = studyLogService.findAllCategories();
        return ResponseEntity.ok(allCategories);
    }
}
