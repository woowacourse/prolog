package wooteco.support.autoceptor.test_classes;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import wooteco.prolog.login.domain.AuthMemberPrincipal;
import wooteco.prolog.member.domain.Member;

@RequestMapping("/api2")
@Controller
public class ControllerClass {

    @GetMapping("/test")
    public void annotationExists(@AuthMemberPrincipal Member member) {
    }

    @DeleteMapping("/test/{testId}")
    public void pattern(@AuthMemberPrincipal Member member, @jakarta.websocket.server.PathParam("testId") Long testId) {

    }
}
