package wooteco.prolog.login.ui.auto_interceptor_register;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import wooteco.prolog.login.domain.AuthMemberPrincipal;
import wooteco.prolog.login.ui.auto_interceptor_register.test_classes.ControllerClass;
import wooteco.prolog.login.ui.auto_interceptor_register.test_classes.RestControllerClass;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

class MethodScannerTest {

    @DisplayName("특정 어노테이션이 파라메터에 적용된 메서드를 반환한다.")
    @Test
    void extractMethodAnnotatedOnParameter() {
        // given
        List<Class<? extends Annotation>> targetAnnotations = Arrays.asList(AuthMemberPrincipal.class);
        List<Class<?>> classes = Arrays.asList(ControllerClass.class, RestControllerClass.class);

        MethodScanner methodScanner = new MethodScanner(targetAnnotations);

        // when
        List<Method> methods = methodScanner.extractMethodAnnotatedOnParameter(classes);

        // then
        List<String> methodName = methods.stream()
                .map(Method::getName)
                .collect(toList());

        assertThat(methodName).containsOnly("annotationExists");

    }
}
