package wooteco.prolog.studylog.ui;

import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wooteco.prolog.member.domain.LoginMember;
import wooteco.prolog.studylog.application.StudylogService;
import wooteco.prolog.studylog.application.dto.StudylogRequest;
import wooteco.prolog.studylog.application.dto.StudylogResponse;
import wooteco.prolog.studylog.application.dto.StudylogsResponse;
import wooteco.prolog.studylog.application.dto.search.SearchParams;
import wooteco.prolog.studylog.application.dto.search.StudylogsSearchRequest;
import wooteco.prolog.studylog.exception.StudylogNotFoundException;
import wooteco.support.number.NumberUtils;
import wooteco.support.security.core.AuthenticationPrincipal;

@RestController
@RequestMapping("/posts")
public class StudylogController {

    private final StudylogService studylogService;

    public StudylogController(StudylogService studylogService) {
        this.studylogService = studylogService;
    }

    @PostMapping
    public ResponseEntity<Void> createStudylog(@AuthenticationPrincipal LoginMember member,
                                               @RequestBody List<StudylogRequest> studylogRequests) {
        List<StudylogResponse> studylogResponse = studylogService
            .insertStudylogs(member, studylogRequests);
        return ResponseEntity.created(URI.create("/posts/" + studylogResponse.get(0).getId()))
            .build();
    }

    @GetMapping
    public ResponseEntity<StudylogsResponse> showAll(
        @SearchParams StudylogsSearchRequest searchRequest) {
        StudylogsResponse studylogsResponse = studylogService.findStudylogs(searchRequest);
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
        @AuthenticationPrincipal LoginMember member,
        @PathVariable Long id,
        @RequestBody StudylogRequest studylogRequest
    ) {
        studylogService.updateStudylog(member, id, studylogRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudylog(@AuthenticationPrincipal LoginMember member,
                                               @PathVariable Long id) {
        studylogService.deleteStudylog(member, id);
        return ResponseEntity.noContent().build();
    }
}
