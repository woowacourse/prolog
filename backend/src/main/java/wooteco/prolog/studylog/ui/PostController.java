package wooteco.prolog.studylog.ui;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
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
import wooteco.prolog.studylog.application.dto.StudylogRequest;
import wooteco.prolog.studylog.application.dto.StudylogResponse;
import wooteco.prolog.studylog.application.dto.StudylogsResponse;
import wooteco.prolog.studylog.application.dto.search.SearchParams;
import wooteco.prolog.studylog.application.dto.search.StudylogsSearchRequest;

import java.net.URI;
import java.util.List;

import static wooteco.prolog.common.exception.BadRequestCode.STUDYLOG_NOT_FOUND;

@Deprecated
@RestController
@RequestMapping("/posts")
public class PostController {

    private final StudylogService studylogService;

    public PostController(StudylogService studylogService) {
        this.studylogService = studylogService;
    }

    @PostMapping
    @MemberOnly
    public ResponseEntity<Void> createStudylog(@AuthMemberPrincipal LoginMember member,
                                               @RequestBody List<StudylogRequest> studylogRequests) {
        List<StudylogResponse> studylogResponses = studylogService.insertStudylogs(member.getId(),
            studylogRequests);
        return ResponseEntity.created(URI.create("/posts/" + studylogResponses.get(0).getId()))
            .build();
    }

    @GetMapping
    public ResponseEntity<StudylogsResponse> showAll(
        @AuthMemberPrincipal LoginMember member,
        @SearchParams StudylogsSearchRequest searchRequest) {
        StudylogsResponse studylogsResponse = studylogService.findStudylogs(searchRequest,
            member.getId(), member.isAnonymous());
        return ResponseEntity.ok(studylogsResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudylogResponse> showStudylog(
        @PathVariable String id,
        @AuthMemberPrincipal LoginMember member
    ) {
        if (!StringUtils.isNumeric(id)) {
            throw new BadRequestException(STUDYLOG_NOT_FOUND);
        }
        return ResponseEntity.ok(
            studylogService.retrieveStudylogById(member, Long.parseLong(id), false));
    }

    @PutMapping("/{id}")
    @MemberOnly
    public ResponseEntity<Void> updateStudylog(
        @AuthMemberPrincipal LoginMember member,
        @PathVariable Long id,
        @RequestBody StudylogRequest studylogRequest
    ) {
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
