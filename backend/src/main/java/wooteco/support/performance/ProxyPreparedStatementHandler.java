package wooteco.support.performance;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class ProxyPreparedStatementHandler implements InvocationHandler {

    private final Object preparedStatement;
    private final PerformanceLoggingForm loggingForm;

    public ProxyPreparedStatementHandler(Object preparedStatement,
                                         PerformanceLoggingForm loggingForm) {
        this.preparedStatement = preparedStatement;
        this.loggingForm = loggingForm;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.getName().equals("executeQuery")) {
            final long startTime = System.currentTimeMillis();
            final Object returnValue = method.invoke(preparedStatement, args);
            loggingForm.addQueryTime(System.currentTimeMillis() - startTime);
            loggingForm.queryCountUp();
            return returnValue;
        }
        return method.invoke(preparedStatement, args);
    }
}
