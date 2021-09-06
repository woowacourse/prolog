package wooteco.support.autoceptor.scanner;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class MethodScanner {

    private final Collection<Class<? extends Annotation>> targetAnnotations;

    public MethodScanner(Class<? extends Annotation>... targetAnnotations) {
        this(Arrays.asList(targetAnnotations));
    }

    public MethodScanner(Collection<Class<? extends Annotation>> targetAnnotations) {
        this.targetAnnotations = targetAnnotations;
    }

    public List<Method> extractMethodAnnotatedOnParameter(Class<?>... controllers) {
        return extractMethodAnnotatedOnParameter(Arrays.asList(controllers));
    }

    public List<Method> extractMethodAnnotatedOnParameter(List<Class<?>> controllers) {
        return controllers.stream()
                .flatMap(controller -> Arrays.stream(controller.getMethods()))
                .filter(this::isAnyMatchedWithTargetAnnotation)
                .collect(toList());
    }

    private boolean isAnyMatchedWithTargetAnnotation(Method method) {
        return Arrays.stream(method.getParameterAnnotations())
                .flatMap(Arrays::stream)
                .map(Annotation::annotationType)
                .anyMatch(targetAnnotations::contains);
    }
}
