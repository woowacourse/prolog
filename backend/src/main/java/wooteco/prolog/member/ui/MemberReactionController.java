package wooteco.prolog.member.ui;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import wooteco.prolog.login.aop.MemberOnly;
import wooteco.prolog.login.domain.AuthMemberPrincipal;
import wooteco.prolog.login.ui.LoginMember;
import wooteco.prolog.member.application.dto.MemberScrapRequest;
import wooteco.prolog.member.application.dto.MemberScrapResponse;
import wooteco.prolog.studylog.application.StudylogScrapService;
import wooteco.prolog.studylog.application.dto.StudylogsResponse;

import java.net.URI;

import static org.springframework.data.domain.Sort.Direction.DESC;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberReactionController {

    private final StudylogScrapService studylogScrapService;

    @GetMapping(value = "/scrap")
    @MemberOnly
    public ResponseEntity<StudylogsResponse> showScrap(
        @AuthMemberPrincipal LoginMember member,
        @PageableDefault(direction = DESC) Pageable pageable
    ) {
        StudylogsResponse studylogsResponse = studylogScrapService
            .showScrap(member.getId(), pageable);
        return ResponseEntity.ok(studylogsResponse);
    }

    @PostMapping(value = "/scrap")
    @MemberOnly
    public ResponseEntity<Void> registerScrap(
        @AuthMemberPrincipal LoginMember member,
        @RequestBody MemberScrapRequest studylogIdRequest
    ) {
        MemberScrapResponse memberScrapResponse = studylogScrapService
            .registerScrap(member.getId(), studylogIdRequest.getStudylogId());
        return ResponseEntity
            .created(URI.create("/studylogs/" + memberScrapResponse.getStudylogResponse().getId()))
            .build();
    }

    @DeleteMapping(value = "/scrap")
    @MemberOnly
    public ResponseEntity<Void> unregisterScrap(
        @AuthMemberPrincipal LoginMember member,
        @RequestParam Long studylog
    ) {
        studylogScrapService.unregisterScrap(member.getId(), studylog);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/{username}/scrap")
    @MemberOnly
    public ResponseEntity<StudylogsResponse> deprecatedShowScrap(
        @AuthMemberPrincipal LoginMember member,
        @PageableDefault(direction = DESC) Pageable pageable
    ) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(URI.create("/members/scrap"));
        return new ResponseEntity<>(httpHeaders, HttpStatus.MOVED_PERMANENTLY);
    }

    @PostMapping(value = "/{username}/scrap")
    @MemberOnly
    public ResponseEntity<Void> deprecatedRegisterScrap(
        @AuthMemberPrincipal LoginMember member,
        @RequestBody MemberScrapRequest studylogIdRequest
    ) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(URI.create("/members/scrap"));
        return new ResponseEntity<>(httpHeaders, HttpStatus.PERMANENT_REDIRECT);
    }

    @DeleteMapping(value = "/{username}/scrap")
    @MemberOnly
    public ResponseEntity<Void> deprecatedUnregisterScrap(
        @AuthMemberPrincipal LoginMember member,
        @RequestParam Long studylog
    ) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(URI.create("/members/scrap?studylog=" + studylog));
        return new ResponseEntity<>(httpHeaders, HttpStatus.PERMANENT_REDIRECT);
    }
}
