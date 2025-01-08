package wooteco.support.autoceptor.scanner;

import java.lang.annotation.Annotation;
import java.lang.reflect.GenericDeclaration;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

public enum MappingAnnotation {
    REQUEST_MAPPING(RequestMapping.class,
        declaration -> Arrays.asList(declaration.getAnnotation(RequestMapping.class).value()),
        () -> {
            throw new IllegalArgumentException(
                "RequestMapping 을 통해 LoginInterceptor 를 등록할 수 없습니다. @GetMapping 과 같은 형식으로 사용해주세요.");
        }),
    GET(GetMapping.class,
        declaration -> Arrays.asList(declaration.getAnnotation(GetMapping.class).value()),
        () -> HttpMethod.GET),
    POST(PostMapping.class,
        declaration -> Arrays.asList(declaration.getAnnotation(PostMapping.class).value()),
        () -> HttpMethod.POST),
    DELETE(DeleteMapping.class,
        declaration -> Arrays.asList(declaration.getAnnotation(DeleteMapping.class).value()),
        () -> HttpMethod.DELETE),
    PUT(PutMapping.class,
        declaration -> Arrays.asList(declaration.getAnnotation(PutMapping.class).value()),
        () -> HttpMethod.PUT),
    PATCH(PatchMapping.class,
        declaration -> Arrays.asList(declaration.getAnnotation(PatchMapping.class).value()),
        () -> HttpMethod.PATCH);

    private final Class<? extends Annotation> typeToken;
    private final Function<GenericDeclaration, List<String>> values;
    private final Supplier<HttpMethod> method;

    MappingAnnotation(Class<? extends Annotation> typeToken, Function<GenericDeclaration, List<String>> values,
                      Supplier<HttpMethod> method) {
        this.typeToken = typeToken;
        this.values = values;
        this.method = method;
    }

    public static List<String> extractUriFrom(GenericDeclaration declaration) {
        List<String> methodUris = Arrays.stream(values())
            .filter(httpMethod -> declaration.isAnnotationPresent(httpMethod.typeToken))
            .findAny()
            .map(httpMethod -> httpMethod.values.apply(declaration))
            .orElseGet(() -> List.of(""));
        if (methodUris.isEmpty()) {
            return List.of("");
        }
        return methodUris;
    }

    public static HttpMethod extractHttpMethod(GenericDeclaration declaration) {
        MappingAnnotation annotation = Arrays.stream(values())
            .filter(httpMethod -> declaration.isAnnotationPresent(httpMethod.typeToken))
            .findAny()
            .orElseThrow(() -> new IllegalArgumentException("해당 HttpMethod 는 로그인 처리에서 고려하지 않았습니다. 필요시 추가하시오"));
        return annotation.method.get();
    }
}
