package wooteco.support.performance.annotationDataExtractor;

import java.lang.reflect.Method;
import wooteco.support.performance.RequestApi;

public interface AnnotationDataExtractor {

    boolean isAssignable(Method method);

    RequestApi extractRequestApi(Method method, String classUrl);
}
