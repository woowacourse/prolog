package wooteco.support.autoceptor.test_classes;

import javax.websocket.server.PathParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import wooteco.prolog.member.domain.LoginMember;
import wooteco.support.security.core.AuthenticationPrincipal;

@RequestMapping("/api2")
@Controller
public class ControllerClass {

    @GetMapping("/test")
    public void annotationExists(@AuthenticationPrincipal LoginMember member) {
    }

    @DeleteMapping("/test/{testId}")
    public void pattern(@AuthenticationPrincipal LoginMember member,
                        @PathParam("testId") Long testId) {

    }
}
