package wooteco.prolog.login.ui.auto_interceptor_uri_mapper;

import org.junit.jupiter.api.Test;
import wooteco.prolog.login.domain.AuthMemberPrincipal;
import wooteco.prolog.login.ui.auto_interceptor_uri_mapper.scanner.ControllerScanner;
import wooteco.prolog.login.ui.auto_interceptor_uri_mapper.scanner.MethodScanner;
import wooteco.prolog.login.ui.auto_interceptor_uri_mapper.scanner.URIScanner;
import wooteco.prolog.login.ui.auto_interceptor_uri_mapper.test_classes.ControllerClass;
import wooteco.prolog.login.ui.auto_interceptor_uri_mapper.test_classes.RestControllerClass;

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
