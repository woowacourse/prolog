package wooteco.support.autoceptor;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import wooteco.prolog.login.domain.AuthMemberPrincipal;
import wooteco.support.autoceptor.scanner.ControllerScanner;
import wooteco.support.autoceptor.scanner.MethodScanner;
import wooteco.support.autoceptor.scanner.URIScanner;
import wooteco.support.autoceptor.test_classes.ControllerClass;
import wooteco.support.autoceptor.test_classes.RestControllerClass;

class URIScannerTest {

    @Test
    void extractUriAndMethods() {
        // given
        List<Class<? extends Annotation>> targetAnnotations =
            Collections.singletonList(AuthMemberPrincipal.class);
        ControllerScanner controllerScanner =
            new ControllerScanner(ControllerClass.class, RestControllerClass.class);
        MethodScanner methodScanner = new MethodScanner(targetAnnotations);

        // when
        List<String> uris = new URIScanner(controllerScanner, methodScanner).extractUri();

        // then
        assertThat(uris).containsOnly(
            "/api2/test",
            "/api2/test/*"
        );
    }

    @Disabled
    @Test
    void extractLoginDetector() {
        // given
        List<Class<? extends Annotation>> targetAnnotations =
            Collections.singletonList(AuthMemberPrincipal.class);
        ControllerScanner controllerScanner =
            new ControllerScanner(ControllerClass.class, RestControllerClass.class);
        MethodScanner methodScanner = new MethodScanner(targetAnnotations);

        URIScanner uriScanner = new URIScanner(controllerScanner, methodScanner);

        List<MethodPattern> expectMethodPatterns = Arrays.asList(
            new MethodPattern(HttpMethod.GET, "/api2/test"),
            new MethodPattern(HttpMethod.DELETE, "/api2/test/*")
        );

        // when
        AuthenticationDetector detector = uriScanner.extractLoginDetector();

        // then
        assertThat(detector).extracting("requireLoginPatterns")
            .usingRecursiveComparison()
            .isEqualTo(expectMethodPatterns);
    }
}
