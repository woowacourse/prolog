package wooteco.support.performance.annotationDataExtractor;

import wooteco.support.performance.RequestApi;

import java.lang.reflect.Method;

public interface AnnotationDataExtractor {

    boolean isAssignable(Method method);

    RequestApi extractRequestApi(Method method, String classUrl);
}
