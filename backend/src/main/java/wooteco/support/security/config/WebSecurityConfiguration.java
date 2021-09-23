package wooteco.support.security.config;

import javax.servlet.Filter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@AllArgsConstructor
public class WebSecurityConfiguration {

    public static final String DEFAULT_FILTER_NAME = "springSecurityFilterChain";

    private WebSecurityConfigurerAdapter adapter;

    @Bean(name = DEFAULT_FILTER_NAME)
    public Filter springSecurityFilterChain() {
        HttpSecurity httpSecurity = new HttpSecurity();
        httpSecurity.apply(adapter);
        return httpSecurity.build();
    }
}
