package wooteco.prolog.common.filter.proxy;

import static java.util.stream.Collectors.joining;

import java.io.BufferedReader;
import java.io.StringReader;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Objects;
import javax.servlet.ServletRequest;

public class ServletRequestCache implements InvocationHandler {
    private static final String TARGET_METHOD_NAME = "getReader";

    private final ServletRequest request;
    private String value = null;

    public ServletRequestCache(ServletRequest request) {
        this.request = request;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if(!method.getName().contains(TARGET_METHOD_NAME)) {
            return method.invoke(request, args);
        }

        if(Objects.isNull(value)) {
            value = request.getReader().lines().collect(joining("\r\n"));
        }

        return new BufferedReader(new StringReader(value));
    }
}
