package wooteco.support.performance.annotationDataExtractor;

import org.springframework.web.bind.annotation.DeleteMapping;
import wooteco.support.performance.RequestApi;

import java.lang.reflect.Method;
import java.util.Arrays;

public class DeleteMappingDataExtractor implements AnnotationDataExtractor {

    @Override
    public boolean isAssignable(Method method) {
        return method.isAnnotationPresent(DeleteMapping.class);
    }

    @Override
    public RequestApi extractRequestApi(Method method, String classUrl) {
        final DeleteMapping deleteMapping = method.getAnnotation(DeleteMapping.class);
        final String api = Arrays.stream(deleteMapping.value())
            .findAny()
            .orElseGet(() ->
                Arrays.stream(deleteMapping.path()).findAny()
                    .orElse("")
            );
        return new RequestApi(classUrl + api, "DELETE");
    }
}
