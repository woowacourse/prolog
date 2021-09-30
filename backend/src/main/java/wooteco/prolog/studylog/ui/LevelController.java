package wooteco.prolog.studylog.ui;

import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wooteco.prolog.report.application.LevelService;
import wooteco.prolog.report.application.dto.LevelRequest;
import wooteco.prolog.report.application.dto.LevelResponse;

@RestController
@RequestMapping("/levels")
@AllArgsConstructor
public class LevelController {

    private final LevelService levelService;

    @GetMapping
    public ResponseEntity<List<LevelResponse>> show() {
        List<LevelResponse> responses = levelService.findAll();
        return ResponseEntity.ok(responses);
    }

    @PostMapping
    public ResponseEntity<LevelResponse> create(@RequestBody LevelRequest levelRequest) {
        LevelResponse levelResponse = levelService.create(levelRequest);
        return ResponseEntity.ok(levelResponse);
    }
}
