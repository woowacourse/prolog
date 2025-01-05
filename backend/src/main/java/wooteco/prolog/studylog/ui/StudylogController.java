package wooteco.prolog.studylog.ui;

import static wooteco.prolog.common.exception.BadRequestCode.STUDYLOG_NOT_FOUND;

import java.net.URI;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wooteco.prolog.common.exception.BadRequestException;
import wooteco.prolog.login.aop.MemberOnly;
import wooteco.prolog.login.domain.AuthMemberPrincipal;
import wooteco.prolog.login.ui.LoginMember;
import wooteco.prolog.studylog.application.StudylogService;
import wooteco.prolog.studylog.application.ViewedStudyLogCookieGenerator;
import wooteco.prolog.studylog.application.dto.StudylogRequest;
import wooteco.prolog.studylog.application.dto.StudylogResponse;
import wooteco.prolog.studylog.application.dto.StudylogTempResponse;
import wooteco.prolog.studylog.application.dto.StudylogsResponse;
import wooteco.prolog.studylog.application.dto.search.SearchParams;
import wooteco.prolog.studylog.application.dto.search.StudylogsSearchRequest;

@RestController
@RequestMapping("/studylogs")
public class StudylogController {

    private final StudylogService studylogService;
    private final ViewedStudyLogCookieGenerator viewedStudyLogCookieGenerator;

    public StudylogController(StudylogService studylogService,
                              ViewedStudyLogCookieGenerator viewedStudyLogCookieGenerator) {
        this.studylogService = studylogService;
        this.viewedStudyLogCookieGenerator = viewedStudyLogCookieGenerator;
    }

    @PostMapping
    @MemberOnly
    public ResponseEntity<StudylogResponse> createStudylog(@AuthMemberPrincipal LoginMember member,
                                                           @RequestBody StudylogRequest studylogRequest) {
        StudylogResponse studylogResponse = studylogService.insertStudylog(member.getId(),
            studylogRequest);
        return ResponseEntity.created(URI.create("/studylogs/" + studylogResponse.getId()))
            .body(studylogResponse);
    }

    @PutMapping("/temp")
    @MemberOnly
    public ResponseEntity<StudylogTempResponse> createStudylogTemp(
        @AuthMemberPrincipal LoginMember member, @RequestBody StudylogRequest studylogRequest) {
        StudylogTempResponse studylogTempResponse = studylogService.insertStudylogTemp(member.getId(), studylogRequest);
        return ResponseEntity.created(URI.create("/studylogs/temp/" + studylogTempResponse.getId()))
            .body(studylogTempResponse);
    }

    @GetMapping("/temp")
    @MemberOnly
    public ResponseEntity<StudylogTempResponse> showStudylogTemp(
        @AuthMemberPrincipal LoginMember member) {
        StudylogTempResponse studylogTempResponse = studylogService.findStudylogTemp(member.getId());
        return ResponseEntity.ok(studylogTempResponse);
    }

    @GetMapping
    public ResponseEntity<StudylogsResponse> showAll(@AuthMemberPrincipal LoginMember member,
                                                     @SearchParams StudylogsSearchRequest searchRequest) {
        StudylogsResponse studylogsResponse = studylogService.findStudylogs(searchRequest,
            member.getId(), member.isAnonymous());
        return ResponseEntity.ok(studylogsResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudylogResponse> showStudylog(@PathVariable String id,
                                                         @AuthMemberPrincipal LoginMember member,
                                                         @CookieValue(name = "viewed", required = false, defaultValue = "/")
                                                         String viewedStudyLogs,
                                                         HttpServletResponse httpServletResponse) {
        if (!StringUtils.isNumeric(id)) {
            throw new BadRequestException(STUDYLOG_NOT_FOUND);
        }

        viewedStudyLogCookieGenerator.setViewedStudyLogCookie(viewedStudyLogs, id, httpServletResponse);
        boolean viewed = viewedStudyLogCookieGenerator.isViewed(viewedStudyLogs, id);
        StudylogResponse body = studylogService.retrieveStudylogById(member, Long.parseLong(id), viewed);
        return ResponseEntity.ok(body);
    }

    @PutMapping("/{id}")
    @MemberOnly
    public ResponseEntity<Void> updateStudylog(@AuthMemberPrincipal LoginMember member,
                                               @PathVariable Long id,
                                               @RequestBody StudylogRequest studylogRequest) {
        studylogService.updateStudylog(member.getId(), id, studylogRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @MemberOnly
    public ResponseEntity<Void> deleteStudylog(@AuthMemberPrincipal LoginMember member,
                                               @PathVariable Long id) {
        studylogService.deleteStudylog(member.getId(), id);
        return ResponseEntity.noContent().build();
    }
}
