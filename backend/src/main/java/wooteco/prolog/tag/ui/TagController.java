package wooteco.prolog.tag.ui;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wooteco.prolog.tag.application.TagService;
import wooteco.prolog.tag.dto.TagRequest;
import wooteco.prolog.tag.dto.TagResponse;

@RestController
@RequestMapping("/tags")
public class TagController {

    private TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @PostMapping
    public ResponseEntity<List<TagResponse>> createTag(@RequestBody List<TagRequest> tagRequests) {
        List<TagResponse> tagResponses = tagService.create(tagRequests);
        return ResponseEntity.ok(tagResponses);
    }

    @GetMapping
    public ResponseEntity<List<TagResponse>> showAll() {
        List<TagResponse> tags = tagService.findAll();
        return ResponseEntity.ok(tags);
    }
}
