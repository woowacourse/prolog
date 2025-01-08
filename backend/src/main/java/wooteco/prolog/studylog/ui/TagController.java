package wooteco.prolog.studylog.ui;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wooteco.prolog.studylog.application.StudylogTagService;
import wooteco.prolog.studylog.application.dto.TagResponse;

import java.util.List;

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
