package wooteco.prolog.login.ui;

import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import wooteco.prolog.login.aop.MemberAuthorityCache;
import wooteco.prolog.login.application.GithubLoginService;
import wooteco.prolog.login.application.JwtTokenProvider;
import wooteco.prolog.login.domain.AuthMemberPrincipal;
import wooteco.support.autoceptor.AutoInterceptorPatternMaker;

@Configuration
@AllArgsConstructor
@Profile("!docu")
public class LoginConfig implements WebMvcConfigurer {

    private static final String BASE_PACKAGE = "wooteco.prolog";

    private final GithubLoginService githubLoginService;
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberAuthorityCache memberAuthorityCache;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        AutoInterceptorPatternMaker mapper =
            new AutoInterceptorPatternMaker(BASE_PACKAGE, AuthMemberPrincipal.class);

        registry.addInterceptor(new LoginInterceptor(githubLoginService, mapper.extractLoginDetector()))
            .addPathPatterns(mapper.extractPatterns());
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(
            new AuthMemberPrincipalArgumentResolver(jwtTokenProvider, memberAuthorityCache));
    }
}
