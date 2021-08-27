package wooteco.support.autoceptor;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import wooteco.support.autoceptor.scanner.ControllerScanner;
import wooteco.support.autoceptor.test_classes.ControllerClass;
import wooteco.support.autoceptor.test_classes.NormalClass;
import wooteco.support.autoceptor.test_classes.RestControllerClass;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ControllerScannerTest {

    @DisplayName("클래스 리스트 중에서 컨트롤러 클래스만 추출한다.")
    @Test
    void extractControllers() {
        // given
        ControllerScanner controllerScanner = new ControllerScanner(
                ControllerClass.class,
                RestControllerClass.class,
                NormalClass.class
        );

        // when
        List<Class<?>> classes = controllerScanner.extractControllers();

        // then
        assertThat(classes).containsOnly(
                ControllerClass.class,
                RestControllerClass.class
        );
    }
}
