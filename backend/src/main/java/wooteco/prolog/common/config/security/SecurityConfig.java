package wooteco.prolog.common.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import wooteco.prolog.common.config.CorsFilter;
import wooteco.prolog.login.application.JwtTokenProvider;
import wooteco.prolog.login.application.dto.GithubProfileResponse;
import wooteco.prolog.login.application.dto.TokenResponse;
import wooteco.prolog.login.excetpion.GithubConnectionException;
import wooteco.prolog.member.application.MemberService;
import wooteco.prolog.member.domain.GithubOAuth2User;
import wooteco.prolog.member.domain.Member;
import wooteco.support.security.authentication.AuthenticationFailureHandler;
import wooteco.support.security.authentication.AuthenticationFilter;
import wooteco.support.security.authentication.AuthenticationProvider;
import wooteco.support.security.authentication.AuthenticationSuccessHandler;
import wooteco.support.security.authentication.OAuth2AccessTokenResponseClient;
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
