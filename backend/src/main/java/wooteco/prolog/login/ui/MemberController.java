package wooteco.prolog.login.ui;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wooteco.prolog.login.application.dto.MemberResponse;
import wooteco.prolog.login.domain.AuthMemberPrincipal;
import wooteco.prolog.login.domain.Member;

@RestController
@RequestMapping("/members")
public class MemberController {

    @GetMapping(value = "/me", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MemberResponse> findMemberInfo(@AuthMemberPrincipal Member member) {
        return ResponseEntity.ok().body(MemberResponse.of(member));
    }
}
