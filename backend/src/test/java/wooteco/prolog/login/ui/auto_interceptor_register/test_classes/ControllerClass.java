package wooteco.prolog.login.ui.auto_interceptor_register.test_classes;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wooteco.prolog.login.domain.AuthMemberPrincipal;
import wooteco.prolog.member.domain.Member;

@RequestMapping("/api2")
@Controller
public class ControllerClass {

    @GetMapping("/test")
    public void annotationExists(@AuthMemberPrincipal Member member) {
    }

}
