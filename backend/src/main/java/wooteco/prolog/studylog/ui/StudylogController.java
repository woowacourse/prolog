package wooteco.prolog.studylog.ui;

import java.net.URI;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wooteco.prolog.login.aop.MemberOnly;
import wooteco.prolog.login.domain.AuthMemberPrincipal;
import wooteco.prolog.login.ui.LoginMember;
import wooteco.prolog.studylog.application.StudylogService;
import wooteco.prolog.studylog.application.ViewedStudyLogCookieGenerator;
import wooteco.prolog.studylog.application.dto.StudylogRequest;
import wooteco.prolog.studylog.application.dto.StudylogResponse;
import wooteco.prolog.studylog.application.dto.StudylogsResponse;
import wooteco.prolog.studylog.application.dto.search.SearchParams;
import wooteco.prolog.studylog.application.dto.search.StudylogsSearchRequest;
import wooteco.prolog.studylog.exception.StudylogNotFoundException;
import wooteco.support.number.NumberUtils;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/studylogs")
public class StudylogController {

    private final StudylogService studylogService;
    private final ViewedStudyLogCookieGenerator viewedStudyLogCookieGenerator;

    public StudylogController(StudylogService studylogService, ViewedStudyLogCookieGenerator viewedStudyLogCookieGenerator) {
        this.studylogService = studylogService;
        this.viewedStudyLogCookieGenerator = viewedStudyLogCookieGenerator;
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

    @GetMapping("/most-popular")
    public ResponseEntity<StudylogsResponse> showPopularStudylogs(@AuthMemberPrincipal LoginMember member, @PageableDefault Pageable pageable) {
        StudylogsResponse studylogsResponse = studylogService.findMostPopularStudylogs(pageable, member.getId(), member.isAnonymous());
        return ResponseEntity.ok(studylogsResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudylogResponse> showStudylog(@PathVariable String id, @AuthMemberPrincipal LoginMember member,
                                                         @CookieValue(name = "viewed", required = false, defaultValue = "/")
                                                                 String viewedStudyLogs,
                                                         HttpServletResponse httpServletResponse) {
        if (!NumberUtils.isNumeric(id)) {
            throw new StudylogNotFoundException();
        }

        viewedStudyLogCookieGenerator.setViewedStudyLogCookie(viewedStudyLogs, id, httpServletResponse);
        return ResponseEntity.ok(studylogService.retrieveStudylogById(member, Long.parseLong(id),
                viewedStudyLogCookieGenerator.isViewed(viewedStudyLogs, id)));
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
