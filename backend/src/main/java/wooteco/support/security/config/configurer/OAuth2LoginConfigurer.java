package wooteco.support.security.config.configurer;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.Filter;
import wooteco.support.security.authentication.AuthenticationFailureHandler;
import wooteco.support.security.authentication.AuthenticationManager;
import wooteco.support.security.authentication.AuthenticationProvider;
import wooteco.support.security.authentication.AuthenticationSuccessHandler;
import wooteco.support.security.authentication.ProviderManager;
import wooteco.support.security.oauth2.OAuth2AccessTokenResponseClient;
import wooteco.support.security.oauth2.OAuth2AuthenticationProvider;
import wooteco.support.security.oauth2.OAuth2LoginAuthenticationFilter;
import wooteco.support.security.oauth2.ClientRegistrationRepository;
import wooteco.support.security.config.HttpSecurity;
import wooteco.support.security.oauth2.OAuth2UserService;

public class OAuth2LoginConfigurer extends AbstractSecurityConfigurer {

    private ClientRegistrationRepository clientRegistrationRepository;
    private AuthenticationSuccessHandler successHandler;
    private AuthenticationFailureHandler failureHandler;
    private OAuth2UserService oAuth2UserService;

    public OAuth2LoginConfigurer(HttpSecurity httpSecurity) {
        super(httpSecurity);
    }

    public OAuth2LoginConfigurer clientRegistrationRepository(
        ClientRegistrationRepository clientRegistrationRepository) {
        this.clientRegistrationRepository = clientRegistrationRepository;
        return this;
    }

    public OAuth2LoginConfigurer successHandler(
        AuthenticationSuccessHandler successHandler) {
        this.successHandler = successHandler;
        return this;
    }

    public OAuth2LoginConfigurer failureHandler(
        AuthenticationFailureHandler failureHandler) {
        this.failureHandler = failureHandler;
        return this;
    }

    public OAuth2LoginConfigurer oAuth2UserService(
        OAuth2UserService oAuth2UserService) {
        this.oAuth2UserService = oAuth2UserService;
        return this;
    }

    @Override
    public void configure(HttpSecurity http) {
        http.addFilter(authenticationFilter());
    }

    private Filter authenticationFilter() {
        return new OAuth2LoginAuthenticationFilter(clientRegistrationRepository,
            authenticationManager(), successHandler, failureHandler);
    }

    private AuthenticationManager authenticationManager() {
        List<AuthenticationProvider> providers = new ArrayList<>();
        providers.add(authenticationProvider());
        return new ProviderManager(providers);
    }

    private OAuth2AuthenticationProvider authenticationProvider() {
        return new OAuth2AuthenticationProvider(tokenResponseClient(), oAuth2UserService);
    }

    private OAuth2AccessTokenResponseClient tokenResponseClient() {
        return new OAuth2AccessTokenResponseClient();
    }

}
