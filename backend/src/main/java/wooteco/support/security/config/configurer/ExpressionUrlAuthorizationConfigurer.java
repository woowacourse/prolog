package wooteco.support.security.config.configurer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import org.springframework.http.HttpMethod;
import wooteco.support.security.access.AccessDecisionManager;
import wooteco.support.security.access.AccessDecisionVoter;
import wooteco.support.security.access.ConfigAttribute;
import wooteco.support.security.access.FilterSecurityInterceptor;
import wooteco.support.security.access.SecurityConfig;
import wooteco.support.security.access.SecurityMetadataSource;
import wooteco.support.security.access.UrlMapping;
import wooteco.support.security.access.matcher.AnyRequestMatcher;
import wooteco.support.security.access.matcher.MvcRequestMatcher;
import wooteco.support.security.access.matcher.RequestMatcher;
import wooteco.support.security.config.HttpSecurity;

@Getter
public class ExpressionUrlAuthorizationConfigurer extends AbstractSecurityConfigurer {

    private final ExpressionInterceptUrlRegistry registry;

    public ExpressionUrlAuthorizationConfigurer(HttpSecurity httpSecurity) {
        super(httpSecurity);
        registry = new ExpressionInterceptUrlRegistry();
    }

    public static String hasRole(String role) {
        return "hasRole('ROLE_" + role + "')";
    }

    @Override
    public void configure(HttpSecurity http) {
        // metadatasource 생성

        SecurityMetadataSource metadataSource = new SecurityMetadataSource(
            registry.createRequestMap());
        AccessDecisionVoter accessDecisionVoter = new AccessDecisionVoter();
        AccessDecisionManager accessDecisionManager = new AccessDecisionManager(accessDecisionVoter);
        FilterSecurityInterceptor filter = new FilterSecurityInterceptor(metadataSource,
            accessDecisionManager);

        http.addFilter(filter);
    }

    public class ExpressionInterceptUrlRegistry {

        private List<UrlMapping> urlMappings = new ArrayList<>();

        public AuthorizedUrl mvcMatchers(HttpMethod method,
                                         String... mvcPatterns) {
            return new AuthorizedUrl(createMvcMatchers(method, mvcPatterns));
        }

        public AuthorizedUrl mvcMatchers(String... mvcPatterns) {
            return new AuthorizedUrl(createMvcMatchers(null, mvcPatterns));
        }

        private List<RequestMatcher> createMvcMatchers(HttpMethod method,
                                                       String... mvcPatterns) {

            return Arrays.stream(mvcPatterns)
                .map(it -> new MvcRequestMatcher(method, it))
                .collect(Collectors.toList());
        }

        public ExpressionUrlAuthorizationConfigurer hasRole(String role) {
            return null;
        }

        public AuthorizedUrl anyRequest() {
            return new AuthorizedUrl(Arrays.asList(AnyRequestMatcher.INSTANCE));
        }

        public HttpSecurity and() {
            return getHttpSecurity();
        }

        public final void addMapping(UrlMapping urlMapping) {
            this.urlMappings.add(urlMapping);
        }

        public LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>> createRequestMap() {
            LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>> requestMap = new LinkedHashMap<>();
            for (UrlMapping mapping : urlMappings) {
                RequestMatcher matcher = mapping.getRequestMatcher();
                Collection<ConfigAttribute> configAttrs = mapping.getConfigAttrs();
                requestMap.put(matcher, configAttrs);
            }
            return requestMap;
        }
    }

    private void interceptUrl(List<RequestMatcher> requestMatchers,
                              Collection<ConfigAttribute> configAttributes) {
        for (RequestMatcher requestMatcher : requestMatchers) {
            registry.addMapping(new UrlMapping(requestMatcher, configAttributes));
        }
    }

    public class AuthorizedUrl {

        public static final String PERMIT_ALL = "permitAll";
        public static final String AUTHENTICATED = "authenticated";

        private List<RequestMatcher> requestMatchers;
        private boolean not;

        public AuthorizedUrl(List<RequestMatcher> requestMatchers) {
            this.requestMatchers = requestMatchers;
        }

        public AuthorizedUrl not() {
            this.not = true;
            return this;
        }

        public ExpressionInterceptUrlRegistry hasRole(String role) {
            return access(ExpressionUrlAuthorizationConfigurer.hasRole(role));
        }

        public ExpressionInterceptUrlRegistry permitAll() {
            return access(PERMIT_ALL);
        }

        public ExpressionInterceptUrlRegistry authenticated() {
            return access(AUTHENTICATED);
        }

        public ExpressionInterceptUrlRegistry access(String attribute) {
            if (not) {
                attribute = "!" + attribute;
            }
            interceptUrl(requestMatchers, SecurityConfig.createList(attribute));
            return ExpressionUrlAuthorizationConfigurer.this.registry;
        }
    }
}
