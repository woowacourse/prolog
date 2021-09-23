package wooteco.prolog.login.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Map;
import javax.servlet.Filter;
import javax.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import wooteco.prolog.login.application.dto.GithubProfileResponse;
import wooteco.prolog.login.excetpion.GithubConnectionException;
import wooteco.prolog.member.application.MemberService;
import wooteco.prolog.member.domain.GithubOAuth2User;
import wooteco.prolog.member.domain.Member;
import wooteco.support.autoceptor.AutoInterceptorPatternMaker;
import wooteco.support.security.authentication.AuthenticationFailureHandler;
import wooteco.support.security.authentication.AuthenticationSuccessHandler;
import wooteco.support.security.authentication.oauth2.OAuth2LoginAuthenticationFilter;
import wooteco.support.security.client.ClientRegistrationRepository;
import wooteco.support.security.config.EnableWebSecurity;
import wooteco.support.security.config.HttpSecurity;
import wooteco.support.security.config.WebSecurityConfigurerAdapter;
import wooteco.support.security.jwt.AuthenticationPrincipal;
import wooteco.support.security.jwt.JwtAuthenticationFilter;
import wooteco.support.security.jwt.JwtAuthenticationProvider;
import wooteco.support.security.jwt.JwtTokenProvider;
import wooteco.support.security.jwt.TokenResponse;
import wooteco.support.security.oauth2user.OAuth2UserService;

@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    public static final String BASE_PACKAGE = "wooteco.prolog";

    private final ClientRegistrationRepository clientRegistrationRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberService memberService;

    @Override
    public void configure(HttpSecurity http) {
        // @formatter:off
        http
            .authorizeRequests()
//                .mvcMatchers("/admin/**").hasRole("")
                .mvcMatchers(authenticatedPatterns()).authenticated()
                .anyRequest().permitAll()
                .and()
            .cors()
                .configurationSource(configurationSource())
                .and()
            .oauth2Login()
                .clientRegistrationRepository(clientRegistrationRepository)
                .successHandler(successHandler())
                .failureHandler(failureHandler())
                .oAuth2UserService(oAuth2UserService())
                .and()
            .addFilterAfter(jwtTokenFilter(), OAuth2LoginAuthenticationFilter.class);
        // @formatter:on
    }

    @NotNull
    private String[] authenticatedPatterns() {
        AutoInterceptorPatternMaker autoInterceptorPatternMaker =
            new AutoInterceptorPatternMaker(BASE_PACKAGE, AuthenticationPrincipal.class);
        List<String> patterns = autoInterceptorPatternMaker.extractPatterns();
        return patterns.toArray(new String[0]);
    }

    private Filter jwtTokenFilter() {
        return new JwtAuthenticationFilter(jwtAuthenticationProvider());
    }

    private JwtAuthenticationProvider jwtAuthenticationProvider() {
        return new JwtAuthenticationProvider(jwtTokenProvider);
    }

    private CorsConfigurationSource configurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOriginPattern("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    private AuthenticationSuccessHandler successHandler() {
        return (request, response, authentication) -> {

            GithubOAuth2User oAuth2User = (GithubOAuth2User) authentication.getPrincipal();
            Member member = memberService.findOrCreateMember(oAuth2User);
            String accessToken = jwtTokenProvider
                .createToken(member.getUsername(), member.getRole().name());

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
}
