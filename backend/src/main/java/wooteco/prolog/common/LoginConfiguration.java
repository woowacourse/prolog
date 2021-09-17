package wooteco.prolog.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import javax.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import wooteco.prolog.login.application.GithubLoginService;
import wooteco.prolog.login.application.dto.TokenRequest;
import wooteco.prolog.login.application.dto.TokenResponse;
import wooteco.prolog.security.AuthenticationFailureHandler;
import wooteco.prolog.security.AuthenticationFilter;
import wooteco.prolog.security.AuthenticationSuccessHandler;

@Configuration
@AllArgsConstructor
public class LoginConfiguration implements WebMvcConfigurer {

    private GithubLoginService githubLoginService;

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
            new AuthenticationFilter(successHandler(), failureHandler()));
        registrationBean.addUrlPatterns("/login/token");
        return registrationBean;
    }

    private AuthenticationSuccessHandler successHandler() {
        return (request, response, authentication) -> {
            TokenRequest tokenRequest = (TokenRequest) authentication.getPrincipal();
            TokenResponse tokenResponse = githubLoginService.createToken(tokenRequest);

            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write(new ObjectMapper().writeValueAsString(tokenResponse));
        };
    }

    private AuthenticationFailureHandler failureHandler() {
        return (request, response, exception) -> {
            throw exception;
        };
    }
}
