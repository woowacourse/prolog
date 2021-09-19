package wooteco.prolog.login.config;

import javax.servlet.DispatcherType;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.web.servlet.DelegatingFilterProxyRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class SecurityFilterConfiguration {

    public static final String DEFAULT_FILTER_NAME = "springSecurityFilterChain";

    @Bean
    @ConditionalOnBean(name = DEFAULT_FILTER_NAME)
    public DelegatingFilterProxyRegistrationBean securityFilterChainRegistration() {
        DelegatingFilterProxyRegistrationBean registration
            = new DelegatingFilterProxyRegistrationBean(DEFAULT_FILTER_NAME);
        registration.setOrder(-100);
        registration.setDispatcherTypes(
            DispatcherType.ASYNC,
            DispatcherType.ERROR,
            DispatcherType.REQUEST);
        return registration;
    }

}
