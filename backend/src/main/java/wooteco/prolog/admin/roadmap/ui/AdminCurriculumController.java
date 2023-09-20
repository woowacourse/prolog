package wooteco.prolog.admin.roadmap.ui;

import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wooteco.prolog.admin.roadmap.application.AdminCurriculumService;
import wooteco.prolog.admin.roadmap.application.dto.CurriculumRequest;
import wooteco.prolog.admin.roadmap.application.dto.CurriculumResponses;

@RestController
@RequestMapping("/admin/curriculums")
public class AdminCurriculumController {

    private final AdminCurriculumService adminCurriculumService;

    public AdminCurriculumController(AdminCurriculumService adminCurriculumService) {
        this.adminCurriculumService = adminCurriculumService;
    }

    @GetMapping
    public ResponseEntity<CurriculumResponses> findAll() {
        return ResponseEntity.ok(adminCurriculumService.findCurriculums());
    }

    @PostMapping
    public ResponseEntity<Void> createCurriculum(@RequestBody CurriculumRequest createRequest) {
        Long curriculumId = adminCurriculumService.create(createRequest);
        return ResponseEntity.created(URI.create("/curriculums/" + curriculumId)).build();
    }

    @PutMapping("/{curriculumId}")
    public ResponseEntity<Void> updateCurriculum(@PathVariable Long curriculumId,
                                                 @RequestBody CurriculumRequest createRequest) {
        adminCurriculumService.update(curriculumId, createRequest);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{curriculumId}")
    public ResponseEntity<Void> deleteCurriculum(@PathVariable Long curriculumId) {
        adminCurriculumService.delete(curriculumId);
        return ResponseEntity.noContent().build();
    }
}
