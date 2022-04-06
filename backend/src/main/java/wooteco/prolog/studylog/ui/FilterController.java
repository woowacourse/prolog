package wooteco.prolog.studylog.ui;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wooteco.prolog.studylog.application.FilterService;
import wooteco.prolog.studylog.application.dto.FilterResponse;

@RestController
@RequestMapping("/filters")
@AllArgsConstructor
public class FilterController {

    private final FilterService filterService;

    @GetMapping
    public ResponseEntity<FilterResponse> showAll() {
        return ResponseEntity.ok().body(filterService.showAll());
    }
}
