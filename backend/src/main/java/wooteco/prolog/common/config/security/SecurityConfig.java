package wooteco.prolog.common.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import wooteco.prolog.common.config.CorsFilter;
import wooteco.prolog.login.application.dto.GithubProfileResponse;
import wooteco.prolog.login.application.dto.TokenResponse;
import wooteco.support.security.authentication.jwt.AuthMemberPrincipal;
import wooteco.prolog.login.excetpion.GithubConnectionException;
import wooteco.support.security.authentication.jwt.AuthMemberPrincipalArgumentResolver;
import wooteco.support.security.authentication.jwt.JwtTokenFilter;
import wooteco.prolog.member.application.MemberService;
import wooteco.prolog.member.domain.GithubOAuth2User;
import wooteco.prolog.member.domain.Member;
import wooteco.support.autoceptor.AutoInterceptorPatternMaker;
import wooteco.support.security.authentication.AuthenticationFailureHandler;
import wooteco.support.security.authentication.AuthenticationFilter;
import wooteco.support.security.authentication.AuthenticationProvider;
import wooteco.support.security.authentication.AuthenticationSuccessHandler;
import wooteco.support.security.authentication.OAuth2AccessTokenResponseClient;
import wooteco.support.security.authentication.jwt.JwtTokenProvider;
import wooteco.support.security.client.ClientRegistrationRepository;
import wooteco.support.security.oauth2user.OAuth2UserService;

@Configuration
@AllArgsConstructor
public class SecurityConfig implements WebMvcConfigurer {

    private final ClientRegistrationRepository clientRegistrationRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberService memberService;

    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilter() {
        FilterRegistrationBean<CorsFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setOrder(1);
        registrationBean.setFilter(new CorsFilter());
        registrationBean.addUrlPatterns("/*");
        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean<AuthenticationFilter> authenticationFilter() {
        FilterRegistrationBean<AuthenticationFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setOrder(2);
        registrationBean.setFilter(
            new AuthenticationFilter(clientRegistrationRepository, authenticationProvider(),
                successHandler(), failureHandler()));
        registrationBean.addUrlPatterns("/login/token");
        return registrationBean;
    }


    private final static String BASE_PACKAGE = "wooteco.prolog";

    @Bean
    public FilterRegistrationBean<JwtTokenFilter> jwtTokenFilterFilter() {
        AutoInterceptorPatternMaker mapper =
            new AutoInterceptorPatternMaker(BASE_PACKAGE, AuthMemberPrincipal.class);

        FilterRegistrationBean<JwtTokenFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setOrder(3);
        registrationBean.setFilter(new JwtTokenFilter(jwtTokenProvider));
        registrationBean.addUrlPatterns(mapper.extractPatterns().toArray(new String[0]));
        return registrationBean;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(authMemberPrincipalArgumentResolver());
    }

    private AuthMemberPrincipalArgumentResolver authMemberPrincipalArgumentResolver() {
        return new AuthMemberPrincipalArgumentResolver(memberService, jwtTokenProvider);
    }

    private AuthenticationProvider authenticationProvider() {
        return new AuthenticationProvider(tokenResponseClient(), oAuth2UserService());
    }

    private OAuth2UserService oAuth2UserService() {
        return oAuth2UserRequest -> {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "token " + oAuth2UserRequest.getAccessToken());

            HttpEntity httpEntity = new HttpEntity<>(headers);
            RestTemplate restTemplate = new RestTemplate();

            try {
                String profileUrl =
                    oAuth2UserRequest.getClientRegistration().getProviderDetails().getUserInfoUri();
                GithubProfileResponse response = restTemplate
                    .exchange(profileUrl, HttpMethod.GET, httpEntity, GithubProfileResponse.class)
                    .getBody();
                return new GithubOAuth2User(new ObjectMapper().convertValue(response, Map.class));

            } catch (HttpClientErrorException e) {
                throw new GithubConnectionException();
            }
        };
    }

    private AuthenticationSuccessHandler successHandler() {
        return (request, response, authentication) -> {

            GithubOAuth2User oAuth2User = (GithubOAuth2User) authentication.getPrincipal();
            Member member = memberService.findOrCreateMember(oAuth2User);
            String accessToken = jwtTokenProvider.createToken(member);

            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter()
                .write(new ObjectMapper().writeValueAsString(TokenResponse.of(accessToken)));
        };
    }

    private AuthenticationFailureHandler failureHandler() {
        return (request, response, exception) -> {
            throw exception;
        };
    }

    private OAuth2AccessTokenResponseClient tokenResponseClient() {
        return new OAuth2AccessTokenResponseClient();
    }
}
