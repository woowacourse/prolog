package wooteco.prolog.common.performance;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyConnectionHandler implements InvocationHandler {

    private final Object connection;
    private final PerformanceLoggingForm loggingForm;

    public ProxyConnectionHandler(Object connection, PerformanceLoggingForm loggingForm) {
        this.connection = connection;
        this.loggingForm = loggingForm;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        final Object returnValue = method.invoke(connection, args);
        if (method.getName().equals("prepareStatement")) {
            return Proxy.newProxyInstance(
                returnValue.getClass().getClassLoader(),
                returnValue.getClass().getInterfaces(),
                new ProxyPreparedStatementHandler(returnValue, loggingForm));
        }
        return returnValue;
    }
}
