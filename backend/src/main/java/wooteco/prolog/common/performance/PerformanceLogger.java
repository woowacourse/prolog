package wooteco.prolog.common.performance;

import java.lang.reflect.Proxy;
import java.sql.Connection;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
@Profile("!prod")
@Aspect
public class PerformanceLogger {

    private ThreadLocal<PerformanceLoggingForm> logForm;

    @Before("@within(org.springframework.transaction.annotation.Transactional) || @annotation(org.springframework.transaction.annotation.Transactional)")
    public void beforeTransaction() {
        getLoggingForm().setTransactionStartTime(System.currentTimeMillis());
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
            getLoggingForm().setTargetApi(request.getRequestURI());
        } catch (IllegalStateException e) {
            getLoggingForm().setTargetApi("dataLoader");
        }
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                getLoggingForm().setTransactionEndTime(System.currentTimeMillis());
                getLoggingForm().printLog();
                getLoggingForm().resetQueryCount();
            }
        });
    }

    @Around("execution(* javax.sql.DataSource.getConnection())")
    public Object datasource(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        final Object proceed = proceedingJoinPoint.proceed();
        return Proxy.newProxyInstance(
            proceed.getClass().getClassLoader(),
            proceed.getClass().getInterfaces(),
            new ProxyConnectionHandler(proceed, getLoggingForm()));
    }

    private PerformanceLoggingForm getLoggingForm() {
        if(logForm == null) {
            logForm = new ThreadLocal<>();
        }

        if(logForm.get() == null) {
            logForm.set(new PerformanceLoggingForm());
        }
        return logForm.get();
    }
}
