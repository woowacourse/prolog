package wooteco.prolog.member.ui;

import static org.springframework.data.domain.Sort.Direction.DESC;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wooteco.prolog.login.domain.AuthMemberPrincipal;
import wooteco.prolog.login.ui.LoginMember;
import wooteco.prolog.studylog.application.StudylogScrapService;
import wooteco.prolog.member.application.dto.MemberScrapRequest;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.studylog.application.dto.StudylogsResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberReactionController {

    private final StudylogScrapService studylogScrapService;

    @GetMapping(value = "/{username}/scrap")
    public ResponseEntity<StudylogsResponse> showScrap(
        @AuthMemberPrincipal LoginMember member,
        @PageableDefault(direction = DESC) Pageable pageable
    ) {
        StudylogsResponse studylogsResponse = studylogScrapService.showScrap(member.getId(), pageable);
        return ResponseEntity.ok(studylogsResponse);
    }

    @PostMapping(value = "/{username}/scrap")
    public ResponseEntity<Void> registerScrap(
        @AuthMemberPrincipal LoginMember member,
        @RequestBody MemberScrapRequest studylogIdRequest
    ) {
        studylogScrapService.registerScrap(member.getId(), studylogIdRequest.getStudylogId());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/{username}/scrap")
    public ResponseEntity<Void> unregisterScrap(
        @AuthMemberPrincipal LoginMember member,
        @RequestBody MemberScrapRequest studylogIdRequest
    ) {
        studylogScrapService.unregisterScrap(member.getId(), studylogIdRequest.getStudylogId());
        return ResponseEntity.ok().build();
    }
}
