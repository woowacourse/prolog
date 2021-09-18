package wooteco.prolog.login.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.Filter;
import javax.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import wooteco.prolog.login.application.dto.GithubProfileResponse;
import wooteco.prolog.login.application.dto.TokenResponse;
import wooteco.prolog.login.excetpion.GithubConnectionException;
import wooteco.prolog.member.application.MemberService;
import wooteco.prolog.member.domain.GithubOAuth2User;
import wooteco.prolog.member.domain.LoginMember;
import wooteco.prolog.member.domain.Member;
import wooteco.support.security.authentication.AuthenticationFailureHandler;
import wooteco.support.security.authentication.AuthenticationProvider;
import wooteco.support.security.authentication.AuthenticationSuccessHandler;
import wooteco.support.security.authentication.OAuth2AccessTokenResponseClient;
import wooteco.support.security.authentication.OAuth2AuthenticationFilter;
import wooteco.support.security.client.ClientRegistrationRepository;
import wooteco.support.security.filter.FilterChainProxy;
import wooteco.support.security.filter.SecurityFilterChain;
import wooteco.support.security.filter.SecurityFilterChainAdaptor;
import wooteco.support.security.jwt.JwtTokenFilter;
import wooteco.support.security.jwt.JwtTokenProvider;
import wooteco.support.security.oauth2user.OAuth2UserService;
import wooteco.support.security.userdetails.UserDetailsService;

@Configuration
@AllArgsConstructor
public class SecurityConfig implements WebMvcConfigurer {

    private final ClientRegistrationRepository clientRegistrationRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberService memberService;

    public static final String DEFAULT_FILTER_NAME = "springSecurityFilterChain";

    @Bean(name = DEFAULT_FILTER_NAME)
    public Filter springSecurityFilterChain() {
        List<SecurityFilterChain> securityFilterChains = new ArrayList<>();
        securityFilterChains.add(SecurityFilterChainAdaptor.of("/*", corsFilter()));
        securityFilterChains
            .add(SecurityFilterChainAdaptor.of("/login/token", authenticationFilter()));
        securityFilterChains.add(SecurityFilterChainAdaptor.of("/*", jwtTokenFilterFilter()));
        return new FilterChainProxy(securityFilterChains);
    }

    private Filter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOriginPattern("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }

    private Filter authenticationFilter() {
        return new OAuth2AuthenticationFilter(
            clientRegistrationRepository, authenticationProvider(),
            successHandler(), failureHandler());
    }

    private Filter jwtTokenFilterFilter() {
        return new JwtTokenFilter(userDetailsService(), jwtTokenProvider);
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
            String accessToken = jwtTokenProvider
                .createToken(member.getId(), member.getRole().name());

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

    private UserDetailsService userDetailsService() {
        return username -> {
            Member member = memberService.findById(Long.parseLong(username));
            return LoginMember.of(member);
        };
    }
}
