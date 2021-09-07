package wooteco.support.autoceptor;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import wooteco.support.autoceptor.scanner.ClassScanner;
import wooteco.support.autoceptor.test_classes.ControllerClass;
import wooteco.support.autoceptor.test_classes.NormalClass;
import wooteco.support.autoceptor.test_classes.RestControllerClass;

class ClassScannerTest {

    @DisplayName("하위 패키지의 모든 컨트롤러를 파싱한다.")
    @Test
    void getAllClasses() {
        // given
        final String basePackage = "wooteco.support.autoceptor.test_classes";
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
