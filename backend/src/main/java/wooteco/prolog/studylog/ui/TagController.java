package wooteco.prolog.studylog.ui;

import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wooteco.prolog.report.application.StudylogTagService;
import wooteco.prolog.report.application.dto.TagResponse;

@RestController
@RequestMapping("/tags")
@AllArgsConstructor
public class TagController {

    private final StudylogTagService studylogTagService;

    @GetMapping
    public ResponseEntity<List<TagResponse>> showAll() {
        List<TagResponse> tags = studylogTagService.findTagsIncludedInStudylogs();
        return ResponseEntity.ok(tags);
    }
}
