package wooteco.prolog.login.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/members")
public class MemberController {

    @GetMapping("/me")
    public ResponseEntity<Void> findMemberInfo() {
        return ResponseEntity.ok().build();
    }
}
