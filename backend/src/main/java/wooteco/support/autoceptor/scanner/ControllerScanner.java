package wooteco.support.autoceptor.scanner;

import static java.util.stream.Collectors.toList;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

public class ControllerScanner {

    private final Collection<Class<?>> classes;

    public ControllerScanner(Class<?>... classes) {
        this(Arrays.asList(classes));
    }

    public ControllerScanner(Collection<Class<?>> classes) {
        this.classes = classes;
    }

    public List<Class<?>> extractControllers() {
        return classes.stream()
            .filter(this::isController)
            .collect(toList());
    }

    private boolean isController(Class<?> typeToken) {
        return typeToken.isAnnotationPresent(Controller.class) ||
            typeToken.isAnnotationPresent(RestController.class);
    }
}
