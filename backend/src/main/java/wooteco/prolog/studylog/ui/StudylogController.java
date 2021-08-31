package wooteco.prolog.studylog.ui;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wooteco.prolog.login.domain.AuthMemberPrincipal;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.studylog.application.StudylogService;
import wooteco.prolog.studylog.application.dto.StudylogRequest;
import wooteco.prolog.studylog.application.dto.StudylogResponse;
import wooteco.prolog.studylog.application.dto.StudylogsResponse;
import wooteco.prolog.studylog.exception.StudylogNotFoundException;
import wooteco.prolog.studylog.infrastructure.NumberUtils;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/studylogs")
public class StudylogController {

    private final StudylogService studylogService;

    public StudylogController(StudylogService studylogService) {
        this.studylogService = studylogService;
    }

    @PostMapping
    public ResponseEntity<Void> createStudylog(@AuthMemberPrincipal Member member,
                                           @RequestBody List<StudylogRequest> studylogRequests) {
        List<StudylogResponse> studylogResponse = studylogService
                .insertStudylogs(member, studylogRequests);
        return ResponseEntity.created(URI.create("/studylogs/" + studylogResponse.get(0).getId())).build();
    }

    @GetMapping
    public ResponseEntity<StudylogsResponse> showAll(
            @RequestParam(required = false) List<Long> levels,
            @RequestParam(required = false) List<Long> missions,
            @RequestParam(required = false) List<Long> tags,
            @RequestParam(required = false) List<String> usernames,
            @PageableDefault(size = 20, direction = Direction.DESC, sort = "id") Pageable pageable
    ) {
        StudylogsResponse studylogsResponse = studylogService
                .findStudylogsWithFilter(levels, missions, tags, usernames, pageable);
        return ResponseEntity.ok(studylogsResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudylogResponse> showStudylog(@PathVariable String id) {
        if (!NumberUtils.isNumeric(id)) {
            throw new StudylogNotFoundException();
        }
        StudylogResponse studylogResponse = studylogService.findById(Long.parseLong(id));
        return ResponseEntity.ok(studylogResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateStudylog(
            @AuthMemberPrincipal Member member,
            @PathVariable Long id,
            @RequestBody StudylogRequest studylogRequest
    ) {
        studylogService.updateStudylog(member, id, studylogRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudylog(@AuthMemberPrincipal Member member,
                                           @PathVariable Long id) {
        studylogService.deleteStudylog(member, id);
        return ResponseEntity.noContent().build();
    }
}
