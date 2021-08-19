package wooteco.prolog.login.ui.auto_interceptor_register;

import org.springframework.web.bind.annotation.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public enum MappingAnnotation {
    REQUEST_MAPPING(RequestMapping.class, declaration -> Arrays.asList(declaration.getAnnotation(RequestMapping.class).value())),
    GET(GetMapping.class, declaration -> Arrays.asList(declaration.getAnnotation(GetMapping.class).value())),
    POST(PostMapping.class, declaration -> Arrays.asList(declaration.getAnnotation(PostMapping.class).value())),
    DELETE(DeleteMapping.class, declaration -> Arrays.asList(declaration.getAnnotation(DeleteMapping.class).value())),
    PUT(PutMapping.class, declaration -> Arrays.asList(declaration.getAnnotation(PutMapping.class).value()));

    private final Class<? extends Annotation> typeToken;
    private final Function<GenericDeclaration, List<String>> values;

    MappingAnnotation(
            Class<? extends Annotation> typeToken,
            Function<GenericDeclaration, List<String>> values
    ) {
        this.typeToken = typeToken;
        this.values = values;
    }

    public static List<String> extractUriFrom(GenericDeclaration declaration) {
        return Arrays.stream(values())
                .filter(httpMethod -> declaration.isAnnotationPresent(httpMethod.typeToken))
                .map(httpMethods -> httpMethods.values.apply(declaration))
                .findAny()
                .orElse(Collections.emptyList());
    }
}
