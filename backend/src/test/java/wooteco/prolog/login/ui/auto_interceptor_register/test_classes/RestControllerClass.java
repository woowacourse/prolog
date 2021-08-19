package wooteco.prolog.login.ui.auto_interceptor_register.test_classes;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api")
@RestController
public class RestControllerClass {

    @GetMapping("/test")
    public void annotationNotExists() {
    }
}
