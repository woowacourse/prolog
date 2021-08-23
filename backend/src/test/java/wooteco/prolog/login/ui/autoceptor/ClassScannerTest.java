package wooteco.prolog.login.ui.autoceptor;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import wooteco.prolog.login.ui.autoceptor.scanner.ClassScanner;
import wooteco.prolog.login.ui.autoceptor.test_classes.ControllerClass;
import wooteco.prolog.login.ui.autoceptor.test_classes.RestControllerClass;
import wooteco.prolog.login.ui.autoceptor.test_classes.NormalClass;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class ClassScannerTest {

    @DisplayName("하위 패키지의 모든 컨트롤러를 파싱한다.")
    @Test
    void getAllClasses() {
        // given
        final String basePackage = "wooteco.prolog.login.ui.autoceptor.test_classes";
        ClassScanner classScanner = new ClassScanner(basePackage);

        // when
        Set<Class<?>> allClasses = classScanner.getAllClasses();

        // then
        assertThat(allClasses).containsOnly(
                ControllerClass.class,
                RestControllerClass.class,
                NormalClass.class
        );
    }
}
