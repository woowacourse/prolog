package wooteco.prolog.login.ui.auto_interceptor_register;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public enum MappingAnnotation {
    GET(GetMapping.class, method -> Arrays.asList(method.getAnnotation(GetMapping.class).value())),
    POST(PostMapping.class, method -> Arrays.asList(method.getAnnotation(PostMapping.class).value())),
    DELETE(DeleteMapping.class, method -> Arrays.asList(method.getAnnotation(DeleteMapping.class).value())),
    PUT(PutMapping.class, method -> Arrays.asList(method.getAnnotation(PutMapping.class).value()));

    private final Class<? extends Annotation> typeToken;
    private final Function<Method, List<String>> values;

    MappingAnnotation(
        Class<? extends Annotation> typeToken,
        Function<Method, List<String>> values
    ) {
        this.typeToken = typeToken;
        this.values = values;
    }

    public static List<String> extractUriFrom(Method method) {
        return Arrays.stream(values())
            .filter(httpMethod -> method.isAnnotationPresent(httpMethod.typeToken))
            .map(httpMethods -> httpMethods.values.apply(method))
            .findAny()
            .orElse(Collections.singletonList(""));
    }
}
