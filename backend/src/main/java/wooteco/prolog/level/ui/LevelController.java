package wooteco.prolog.level.ui;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wooteco.prolog.level.application.LevelService;
import wooteco.prolog.level.application.dto.LevelRequest;
import wooteco.prolog.level.application.dto.LevelResponse;

import java.util.List;

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