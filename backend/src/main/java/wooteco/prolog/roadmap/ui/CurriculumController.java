package wooteco.prolog.roadmap.ui;

import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wooteco.prolog.roadmap.application.CurriculumService;
import wooteco.prolog.roadmap.application.dto.CurriculumRequest;
import wooteco.prolog.roadmap.application.dto.CurriculumResponses;

@RestController
@RequestMapping("/curriculums")
public class CurriculumController {

    private final CurriculumService curriculumService;

    public CurriculumController(CurriculumService curriculumService) {
        this.curriculumService = curriculumService;
    }

    @PostMapping
    public ResponseEntity<Void> createCurriculum(@RequestBody CurriculumRequest createRequest) {
        Long curriculumId = curriculumService.create(createRequest);
        return ResponseEntity.created(URI.create("/curriculums/" + curriculumId)).build();
    }

    @GetMapping
    public ResponseEntity<CurriculumResponses> findAll() {
        return ResponseEntity.ok(curriculumService.findCurriculums());
    }
}
