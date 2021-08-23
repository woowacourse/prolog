package wooteco.prolog.login.ui.autoceptor;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import wooteco.prolog.login.ui.autoceptor.scanner.ClassScanner;
import wooteco.prolog.login.ui.autoceptor.scanner.ControllerScanner;
import wooteco.prolog.login.ui.autoceptor.scanner.MethodScanner;
import wooteco.prolog.login.ui.autoceptor.scanner.URIScanner;

public class AutoInterceptorPatternMaker {

    private final URIScanner uriScanner;

    @SafeVarargs
    public AutoInterceptorPatternMaker(
        String basePackage,
        Class<? extends Annotation>... targetAnnotations
    ) {
        this(basePackage, Arrays.asList(targetAnnotations));
    }

    public AutoInterceptorPatternMaker(
        String basePackage,
        List<Class<? extends Annotation>> targetAnnotations
    ) {
        this.uriScanner = new URIScanner(
                createControllerScanner(basePackage),
                createMethodScanner(targetAnnotations)
            );
    }

    private ControllerScanner createControllerScanner(String basePackage) {
        return new ControllerScanner(getAllClasses(basePackage));
    }

    private Set<Class<?>> getAllClasses(String basePackage) {
        return new ClassScanner(basePackage).getAllClasses();
    }

    private MethodScanner createMethodScanner(List<Class<? extends Annotation>> targetAnnotations) {
        return new MethodScanner(targetAnnotations);
    }

    public List<String> extractPatterns() {
        return uriScanner.extractUri();
    }
}
