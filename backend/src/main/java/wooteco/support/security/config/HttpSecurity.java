package wooteco.support.security.config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.servlet.Filter;
import lombok.Getter;
import wooteco.support.security.config.configurer.CorsConfigurer;
import wooteco.support.security.config.configurer.ExpressionUrlAuthorizationConfigurer;
import wooteco.support.security.config.configurer.OAuth2LoginConfigurer;
import wooteco.support.security.config.configurer.SecurityConfigurer;
import wooteco.support.security.config.configurer.SecurityContextConfigurer;
import wooteco.support.security.filter.DefaultSecurityFilterChain;
import wooteco.support.security.filter.FilterChainProxy;
import wooteco.support.security.filter.FilterComparator;

@Getter
public class HttpSecurity {

    private List<Filter> filters = new ArrayList<>();
    private final List<SecurityConfigurer> configurers = new ArrayList();
    private FilterComparator comparator = new FilterComparator();

    public HttpSecurity and() {
        return this;
    }

    public SecurityContextConfigurer securityContext() {
        SecurityContextConfigurer configurer = new SecurityContextConfigurer(this);
        apply(configurer);
        return configurer;
    }

    public CorsConfigurer cors() {
        CorsConfigurer configurer = new CorsConfigurer(this);
        apply(configurer);
        return configurer;
    }

    public OAuth2LoginConfigurer oauth2Login() {
        OAuth2LoginConfigurer configurer = new OAuth2LoginConfigurer(this);
        apply(configurer);
        return configurer;
    }

    public ExpressionUrlAuthorizationConfigurer.ExpressionInterceptUrlRegistry authorizeRequests() {
        ExpressionUrlAuthorizationConfigurer configurer = new ExpressionUrlAuthorizationConfigurer(
            this);
        apply(configurer);
        return configurer.getRegistry();
    }

    public void apply(SecurityConfigurer configurer) {
        configurers.add(configurer);
    }

    public HttpSecurity addFilter(Filter filter) {
        filters.add(filter);
        return this;
    }

    public Filter build() {
        init();

        configure();

        return performBuild();
    }

    private void init() {
        configurers.get(0).configure(this);
        configurers.remove(0);
    }

    private void configure() {
        this.securityContext();
        for (SecurityConfigurer configurer : configurers) {
            configurer.configure(this);
        }
    }

    private FilterChainProxy performBuild() {
        filters.sort(comparator);
        DefaultSecurityFilterChain securityFilterChain = new DefaultSecurityFilterChain(filters);

        return new FilterChainProxy(
            Collections.singletonList(securityFilterChain));
    }

    public HttpSecurity addFilterAfter(Filter filter, Class<? extends Filter> afterFilter) {
        comparator.registerAfter(filter.getClass(), afterFilter);
        return addFilter(filter);
    }

    public HttpSecurity addFilterBefore(Filter filter,
                                        Class<? extends Filter> beforeFilter) {
        comparator.registerBefore(filter.getClass(), beforeFilter);
        return addFilter(filter);
    }
}
