package wooteco.prolog.studylog.ui;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wooteco.prolog.studylog.application.TagService;
import wooteco.prolog.studylog.application.dto.TagResponse;

@RestController
@RequestMapping("/tags")
@AllArgsConstructor
public class TagController {

    private TagService tagService;

    @GetMapping
    public ResponseEntity<List<TagResponse>> showAll(HttpServletRequest httpRequest) {
        List<TagResponse> tags = tagService.findAll();
        return ResponseEntity.ok(tags);
    }
}
