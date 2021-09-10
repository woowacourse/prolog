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
import wooteco.prolog.login.domain.AuthMemberPrincipal;
import wooteco.prolog.member.application.MemberService;
import wooteco.prolog.member.application.dto.MemberResponse;
import wooteco.prolog.member.application.dto.MemberUpdateRequest;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.studylog.application.dto.ability.AbilityResponse;

@RestController
@AllArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private MemberService memberService;

    @GetMapping(value = "/me", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MemberResponse> findMemberInfoOfMine(@AuthMemberPrincipal Member member) {
        return ResponseEntity.ok().body(MemberResponse.of(member));
    }

    @GetMapping(value = "/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MemberResponse> findMemberInfo(@AuthMemberPrincipal Member member) {
        return ResponseEntity.ok().body(MemberResponse.of(member));
    }

    @PutMapping("/me")
    public ResponseEntity<Void> updateStudylog(
        @AuthMemberPrincipal Member member,
        @RequestBody MemberUpdateRequest updateRequest
    ) {
        memberService.updateMember(member, updateRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{memberId}/abilities")
    public ResponseEntity<List<AbilityResponse>> findAbilitiesOfMember(@AuthMemberPrincipal Member member, @PathVariable Long memberId) {
        return ResponseEntity.ok(memberService.findMemberAbilities(memberId));
    }
}
