package wooteco.prolog.member.ui;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wooteco.prolog.member.domain.LoginMember;
import wooteco.support.security.jwt.AuthenticationPrincipal;
import wooteco.prolog.member.application.MemberService;
import wooteco.prolog.member.application.dto.MemberResponse;
import wooteco.prolog.member.application.dto.MemberUpdateRequest;

@RestController
@AllArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private MemberService memberService;

    @GetMapping(value = "/me", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MemberResponse> findMemberInfoOfMine(@AuthenticationPrincipal LoginMember member) {
        return ResponseEntity.ok().body(MemberResponse.of(member));
    }

    @GetMapping(value = "/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MemberResponse> findMemberInfo(@AuthenticationPrincipal LoginMember member) {
        return ResponseEntity.ok().body(MemberResponse.of(member));
    }

    @PutMapping("/me")
    public ResponseEntity<Void> updateStudylog(
        @AuthenticationPrincipal LoginMember member,
        @RequestBody MemberUpdateRequest updateRequest
    ) {
        memberService.updateMember(member, updateRequest);
        return ResponseEntity.ok().build();
    }
}
