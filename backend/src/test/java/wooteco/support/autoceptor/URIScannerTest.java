package wooteco.support.autoceptor;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import wooteco.prolog.login.domain.AuthMemberPrincipal;
import wooteco.support.autoceptor.scanner.ControllerScanner;
import wooteco.support.autoceptor.scanner.MethodScanner;
import wooteco.support.autoceptor.scanner.URIScanner;
import wooteco.support.autoceptor.test_classes.ControllerClass;
import wooteco.support.autoceptor.test_classes.RestControllerClass;

class URIScannerTest {

    @Test
    void extractUriAndMethods() {
        List<Class<? extends Annotation>> targetAnnotations =
            Collections.singletonList(AuthMemberPrincipal.class);
        ControllerScanner controllerScanner =
            new ControllerScanner(ControllerClass.class, RestControllerClass.class);
        MethodScanner methodScanner = new MethodScanner(targetAnnotations);

        List<String> uris = new URIScanner(controllerScanner, methodScanner).extractUri();

        assertThat(uris).containsOnly(
            "/api2/test",
            "/api2/test/*"
        );
    }
}
