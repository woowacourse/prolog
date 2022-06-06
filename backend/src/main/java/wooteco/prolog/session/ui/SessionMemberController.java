package wooteco.prolog.session.ui;

import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wooteco.prolog.login.domain.AuthMemberPrincipal;
import wooteco.prolog.login.ui.LoginMember;
import wooteco.prolog.member.application.dto.MemberResponse;
import wooteco.prolog.session.application.SessionMemberService;
import wooteco.prolog.session.application.dto.SessionGroupMemberRequest;

@RestController
@RequestMapping("/sessions/{sessionId}/members")
@AllArgsConstructor
public class SessionMemberController {

    private final SessionMemberService sessionMemberService;

//    // admin only
//    @PostMapping
//    public ResponseEntity<Void> register(@PathVariable Long sessionId, @RequestBody SessionMemberRequest sessionMemberRequest) {
//        sessionMemberService.registerMembers(sessionId, sessionMemberRequest);
//        return ResponseEntity.ok().build();
//    }

    @PostMapping("/me")
    public ResponseEntity<Void> registerMe(@PathVariable Long sessionId,
        @AuthMemberPrincipal LoginMember member) {
        sessionMemberService.registerMember(sessionId, member.getId());
        return ResponseEntity.ok().build();
    }

    // admin only
    @PostMapping
    public ResponseEntity<Void> registerByGroupId(@PathVariable Long sessionId,
        @RequestBody SessionGroupMemberRequest sessionGroupMemberRequest) {
        sessionMemberService.registerMembersByGroupId(sessionId, sessionGroupMemberRequest);
        return ResponseEntity.ok().build();
    }

    // admin only
    @GetMapping
    public ResponseEntity<List<MemberResponse>> findAll(@PathVariable Long sessionId) {
        List<MemberResponse> responses = sessionMemberService.findAllMembersBySessionId(sessionId);
        return ResponseEntity.ok(responses);
    }
}
