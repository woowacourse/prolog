package wooteco.prolog.studylog.ui;

import java.net.URI;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wooteco.prolog.login.aop.MemberOnly;
import wooteco.prolog.login.domain.AuthMemberPrincipal;
import wooteco.prolog.login.ui.LoginMember;
import wooteco.prolog.studylog.application.StudylogService;
import wooteco.prolog.studylog.application.dto.StudylogRequest;
import wooteco.prolog.studylog.application.dto.StudylogResponse;
import wooteco.prolog.studylog.application.dto.StudylogsResponse;
import wooteco.prolog.studylog.application.dto.search.SearchParams;
import wooteco.prolog.studylog.application.dto.search.StudylogsSearchRequest;
import wooteco.prolog.studylog.exception.StudylogNotFoundException;
import wooteco.support.number.NumberUtils;

@RestController
@RequestMapping("/studylogs")
public class StudylogController {

    private final StudylogService studylogService;

    public StudylogController(StudylogService studylogService) {
        this.studylogService = studylogService;
    }

    @PostMapping
    @MemberOnly
    public ResponseEntity<StudylogResponse> createStudylog(@AuthMemberPrincipal LoginMember member, @RequestBody List<StudylogRequest> studylogRequests) {
        List<StudylogResponse> studylogResponse = studylogService.insertStudylogs(member.getId(), studylogRequests);
        return ResponseEntity.created(URI.create("/studylogs/" + studylogResponse.get(0).getId())).body(studylogResponse.get(0));
    }

    @GetMapping
    public ResponseEntity<StudylogsResponse> showAll(@AuthMemberPrincipal LoginMember member, @SearchParams StudylogsSearchRequest searchRequest) {
        StudylogsResponse studylogsResponse = studylogService.findStudylogs(searchRequest, member.getId(), member.isAnonymous());
        return ResponseEntity.ok(studylogsResponse);
    }

    /**
     * 갱신할 스터디로그 개수를 지정해야하기 때문에 pageable 필요
     */
    @PutMapping("/popular")
    public ResponseEntity<Void> updatePopularStudylogs(@PageableDefault Pageable pageable) {
        studylogService.updateMostPopularStudylogs(pageable);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/popular")
    public ResponseEntity<StudylogsResponse> showPopularStudylogs(@AuthMemberPrincipal LoginMember member, @PageableDefault Pageable pageable) {
        StudylogsResponse studylogsResponse = studylogService.findMostPopularStudylogs(pageable, member.getId(), member.isAnonymous());
        return ResponseEntity.ok(studylogsResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudylogResponse> showStudylog(@PathVariable String id, @AuthMemberPrincipal LoginMember member) {
        if (!NumberUtils.isNumeric(id)) {
            throw new StudylogNotFoundException();
        }
        return ResponseEntity.ok(studylogService.retrieveStudylogById(member, Long.parseLong(id)));
    }

    @PutMapping("/{id}")
    @MemberOnly
    public ResponseEntity<Void> updateStudylog(@AuthMemberPrincipal LoginMember member, @PathVariable Long id, @RequestBody StudylogRequest studylogRequest) {
        studylogService.updateStudylog(member.getId(), id, studylogRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @MemberOnly
    public ResponseEntity<Void> deleteStudylog(@AuthMemberPrincipal LoginMember member, @PathVariable Long id) {
        studylogService.deleteStudylog(member.getId(), id);
        return ResponseEntity.noContent().build();
    }
}
