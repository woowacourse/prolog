package wooteco.prolog.member.ui;

import static org.springframework.data.domain.Sort.Direction.DESC;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
import wooteco.prolog.member.application.MemberService;
import wooteco.prolog.member.application.dto.MemberResponse;
import wooteco.prolog.member.application.dto.MemberUpdateRequest;
import wooteco.prolog.member.application.dto.MembersResponse;

@RestController
@AllArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private MemberService memberService;

    @GetMapping(value = "/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MemberResponse> findMemberInfo(@PathVariable String username) {
        return ResponseEntity.ok().body(MemberResponse.of(memberService.findByUsername(username)));
    }

    @PutMapping("/{username}")
    public ResponseEntity<Void> updateMember(
        @AuthMemberPrincipal LoginMember member,
        @PathVariable String username,
        @RequestBody MemberUpdateRequest updateRequest
    ) {
        memberService.updateMember(member, username, updateRequest);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/promote")
    public ResponseEntity<Void> requestPromotion(
            @AuthMemberPrincipal LoginMember member
    ) {
        memberService.requestPromote(member);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/promote")
    public ResponseEntity<MembersResponse> showRequestPromotion(@PageableDefault(direction = DESC, sort = "id") Pageable pageable) {
        MembersResponse response = memberService.findAllByIsPromotionRequestTrue(pageable);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{memberId}/promote/approve")
    public ResponseEntity<Void> applyPromotion(@PathVariable Long memberId) {
        memberService.applyPromote(memberId);
        return ResponseEntity.noContent().build();
    }

    // admin only
    @GetMapping
    public ResponseEntity<MembersResponse> show(@PageableDefault(direction = DESC, sort = "id") Pageable pageable) {
        MembersResponse response = memberService.findAll(pageable);
        return ResponseEntity.ok(response);
    }

    @Deprecated
    @PutMapping("/me")
    @MemberOnly
    public ResponseEntity<Void> updateMember_deprecated(
        @AuthMemberPrincipal LoginMember member,
        @RequestBody MemberUpdateRequest updateRequest
    ) {
        memberService.updateMember_deprecated(member.getId(), updateRequest);
        return ResponseEntity.ok().build();
    }

    @Deprecated
    @GetMapping(value = "/me", produces = MediaType.APPLICATION_JSON_VALUE)
    @MemberOnly
    public ResponseEntity<MemberResponse> findMemberInfoOfMine(@AuthMemberPrincipal LoginMember member) {
        return ResponseEntity.ok().body(MemberResponse.of(memberService.findById(member.getId())));
    }
}
