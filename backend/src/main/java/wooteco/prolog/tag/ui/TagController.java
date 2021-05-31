package wooteco.prolog.tag.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wooteco.prolog.tag.TagService;
import wooteco.prolog.tag.dto.TagRequest;
import wooteco.prolog.tag.dto.TagResponse;

import java.util.List;

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
