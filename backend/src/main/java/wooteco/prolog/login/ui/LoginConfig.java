package wooteco.prolog.login.ui;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import wooteco.prolog.login.application.GithubLoginService;
import wooteco.prolog.login.application.JwtTokenProvider;
import wooteco.prolog.login.application.MemberService;

import java.util.List;

@Configuration
@AllArgsConstructor
public class LoginConfig implements WebMvcConfigurer {

    private final GithubLoginService githubLoginService;
    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor(githubLoginService))
                .addPathPatterns("/members/**")
                .excludePathPatterns("/**");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new AuthMemberPrincipalArgumentResolver(memberService, jwtTokenProvider));
    }
}
