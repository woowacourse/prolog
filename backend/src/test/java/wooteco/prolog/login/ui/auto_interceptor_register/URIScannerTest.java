package wooteco.prolog.login.ui.auto_interceptor_register;

import org.junit.jupiter.api.Test;
import wooteco.prolog.login.domain.AuthMemberPrincipal;
import wooteco.prolog.login.ui.auto_interceptor_register.scanner.ControllerScanner;
import wooteco.prolog.login.ui.auto_interceptor_register.scanner.MethodScanner;
import wooteco.prolog.login.ui.auto_interceptor_register.scanner.URIScanner;
import wooteco.prolog.login.ui.auto_interceptor_register.test_classes.ControllerClass;
import wooteco.prolog.login.ui.auto_interceptor_register.test_classes.RestControllerClass;

import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class URIScannerTest {

    @Test
    void extractUriAndMethods() {
        List<Class<? extends Annotation>> targetAnnotations = Collections.singletonList(AuthMemberPrincipal.class);
        ControllerScanner controllerScanner = new ControllerScanner(ControllerClass.class, RestControllerClass.class);
        MethodScanner methodScanner = new MethodScanner(targetAnnotations);

        List<String> uris = new URIScanner(controllerScanner, methodScanner).extractUri();

        assertThat(uris).containsOnly(
                "/api2/test"
        );
    }
}
