package wooteco.support.utils;

import com.google.common.base.CaseFormat;
import java.lang.reflect.Method;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.InvocationInterceptor;
import org.junit.jupiter.api.extension.ReflectiveInvocationContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import wooteco.prolog.common.DataInitializer;

public class PersistenceRollbackExtension implements InvocationInterceptor {

    @Override
    public void interceptBeforeEachMethod(Invocation<Void> invocation,
                                          ReflectiveInvocationContext<Method> invocationContext,
                                          ExtensionContext extensionContext) throws Throwable {
        invokeInitializer(extensionContext);

        invocation.proceed();
    }

    @Override
    public void interceptTestMethod(Invocation<Void> invocation,
                                    ReflectiveInvocationContext<Method> invocationContext,
                                    ExtensionContext extensionContext) throws Throwable {
        if (neverBeenInvoked(invocationContext)) {
            invokeInitializer(extensionContext);
        }

        invocation.proceed();
    }

    private boolean neverBeenInvoked(ReflectiveInvocationContext<Method> invocationContext) {
        return !Arrays.stream(invocationContext.getTargetClass().getDeclaredMethods())
            .filter(it -> it.isAnnotationPresent(BeforeEach.class)).findFirst().isPresent();
    }

    private void invokeInitializer(ExtensionContext extensionContext) {
        String beanName = CaseFormat.UPPER_CAMEL
            .to(CaseFormat.LOWER_CAMEL, DataInitializer.class.getSimpleName());

        DataInitializer dataInitializer = ((DataInitializer) SpringExtension
            .getApplicationContext(extensionContext).getBean(beanName));
        dataInitializer.execute();
    }
}
