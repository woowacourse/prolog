package wooteco.prolog.session.ui;

import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wooteco.prolog.session.application.LevelService;
import wooteco.prolog.session.application.dto.LevelRequest;
import wooteco.prolog.session.application.dto.LevelResponse;

@RestController
@RequestMapping("/levels")
@AllArgsConstructor
public class LevelController {

    private final LevelService levelService;

    @PostMapping
    public ResponseEntity<LevelResponse> create(@RequestBody LevelRequest levelRequest) {
        LevelResponse levelResponse = levelService.create(levelRequest);
        return ResponseEntity.ok(levelResponse);
    }

    @GetMapping
    public ResponseEntity<List<LevelResponse>> show() {
        List<LevelResponse> responses = levelService.findAll();
        return ResponseEntity.ok(responses);
    }
}
