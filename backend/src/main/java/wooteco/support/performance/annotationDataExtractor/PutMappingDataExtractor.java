package wooteco.support.performance.annotationDataExtractor;

import java.lang.reflect.Method;
import java.util.Arrays;
import org.springframework.web.bind.annotation.PutMapping;
import wooteco.support.performance.RequestApi;

public class PutMappingDataExtractor implements AnnotationDataExtractor {

    @Override
    public boolean isAssignable(Method method) {
        return method.isAnnotationPresent(PutMapping.class);
    }

    @Override
    public RequestApi extractRequestApi(Method method, String classUrl) {
        final PutMapping putMapping = method.getAnnotation(PutMapping.class);
        final String api = Arrays.stream(putMapping.value())
            .findAny()
            .orElseGet(() ->
                           Arrays.stream(putMapping.path()).findAny()
                               .orElse("")
            );
        return new RequestApi(classUrl + api, "PUT");
    }
}
