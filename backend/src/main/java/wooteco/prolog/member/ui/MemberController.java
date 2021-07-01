package wooteco.prolog.member.ui;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wooteco.prolog.login.domain.AuthMemberPrincipal;
import wooteco.prolog.member.application.MemberService;
import wooteco.prolog.member.application.dto.MemberResponse;
import wooteco.prolog.member.application.dto.MemberUpdateRequest;
import wooteco.prolog.member.domain.Member;

@RestController
@RequestMapping("/members")
public class MemberController {
    private MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping(value = "/me", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MemberResponse> findMemberInfoOfMine(@AuthMemberPrincipal Member member) {
        return ResponseEntity.ok().body(MemberResponse.of(member));
    }

    @GetMapping(value = "/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MemberResponse> findMemberInfo(@PathVariable String username) {
        MemberResponse member = memberService.findMemberByUsername(username);
        return ResponseEntity.ok().body(member);
    }

    @PutMapping("/{username}")
    public ResponseEntity<Void> updatePost(
            @AuthMemberPrincipal Member member,
            @PathVariable String username,
            @RequestBody MemberUpdateRequest updateRequest
    ) {
        memberService.updateMember(member, username, updateRequest);
        return ResponseEntity.ok().build();
    }
}
