package wooteco.support.performance.annotationDataExtractor;

import java.lang.reflect.Method;
import java.util.Arrays;
import org.springframework.web.bind.annotation.PostMapping;
import wooteco.support.performance.RequestApi;

public class PostMappingDataExtractor implements AnnotationDataExtractor {

    @Override
    public boolean isAssignable(Method method) {
        return method.isAnnotationPresent(PostMapping.class);
    }

    @Override
    public RequestApi extractRequestApi(Method method, String classUrl) {
        final PostMapping postMapping = method.getAnnotation(PostMapping.class);
        final String api = Arrays.stream(postMapping.value())
            .findAny()
            .orElseGet(() ->
                Arrays.stream(postMapping.path()).findAny().orElse("")
            );
        return new RequestApi(classUrl + api, "POST");
    }
}
