package wooteco.support.security.config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.servlet.Filter;
import lombok.Getter;
import org.springframework.web.cors.CorsConfigurationSource;
import wooteco.support.security.authentication.AuthenticationFailureHandler;
import wooteco.support.security.authentication.AuthenticationSuccessHandler;
import wooteco.support.security.client.ClientRegistrationRepository;
import wooteco.support.security.config.configurer.CorsConfigurer;
import wooteco.support.security.config.configurer.OAuth2LoginConfigurer;
import wooteco.support.security.config.configurer.SecurityConfigurer;
import wooteco.support.security.config.configurer.SecurityContextConfigurer;
import wooteco.support.security.filter.DefaultSecurityFilterChain;
import wooteco.support.security.filter.FilterChainProxy;
import wooteco.support.security.filter.FilterComparator;
import wooteco.support.security.oauth2user.OAuth2UserService;

@Getter
public class HttpSecurity {

    private List<Filter> filters = new ArrayList<>();
    private final List<SecurityConfigurer> configurers = new ArrayList();
    private FilterComparator comparator = new FilterComparator();

    public HttpSecurity and() {
        return this;
    }

    public HttpSecurity securityContext() {
        apply(new SecurityContextConfigurer());
        return this;
    }

    public HttpSecurity cors(CorsConfigurationSource source) {
        apply(new CorsConfigurer().configurationSource(source));
        return this;
    }

    public HttpSecurity oauth2Login(ClientRegistrationRepository clientRegistrationRepository,
                                    AuthenticationSuccessHandler successHandler,
                                    AuthenticationFailureHandler failureHandler,
                                    OAuth2UserService oAuth2UserService) {
        apply(new OAuth2LoginConfigurer()
            .clientRegistrationRepository(clientRegistrationRepository)
            .successHandler(successHandler)
            .failureHandler(failureHandler)
            .oAuth2UserService(oAuth2UserService));
        return this;
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
