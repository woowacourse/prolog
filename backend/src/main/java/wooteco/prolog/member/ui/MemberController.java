package wooteco.prolog.member.ui;

import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wooteco.prolog.login.aop.MemberOnly;
import wooteco.prolog.login.domain.AuthMemberPrincipal;
import wooteco.prolog.login.ui.LoginMember;
import wooteco.prolog.member.application.MemberService;
import wooteco.prolog.member.application.dto.MemberResponse;
import wooteco.prolog.member.application.dto.MemberUpdateRequest;
<<<<<<< HEAD
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.report.application.dto.ability.AbilityResponse;
=======
>>>>>>> 865db151960ee58df685dc973a02785125394235

@RestController
@AllArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private MemberService memberService;

    @GetMapping(value = "/me", produces = MediaType.APPLICATION_JSON_VALUE)
    @MemberOnly
    public ResponseEntity<MemberResponse> findMemberInfoOfMine(@AuthMemberPrincipal LoginMember member) {
        return ResponseEntity.ok().body(MemberResponse.of(memberService.findById(member.getId())));
    }

    @GetMapping(value = "/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MemberResponse> findMemberInfo(@PathVariable String username) {
        return ResponseEntity.ok().body(MemberResponse.of(memberService.findByUsername(username)));
    }

    @PutMapping("/me")
    @MemberOnly
    public ResponseEntity<Void> updateStudylog(
        @AuthMemberPrincipal LoginMember member,
        @RequestBody MemberUpdateRequest updateRequest
    ) {
        memberService.updateMember(member.getId(), updateRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{memberId}/abilities")
    public ResponseEntity<List<AbilityResponse>> findAbilitiesOfMember(@AuthMemberPrincipal Member member, @PathVariable Long memberId) {
        return ResponseEntity.ok(memberService.findMemberAbilities(memberId));
    }
}
