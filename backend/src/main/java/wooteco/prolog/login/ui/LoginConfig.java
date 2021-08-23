package wooteco.prolog.login.ui;

import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import wooteco.prolog.login.domain.AuthMemberPrincipal;
import wooteco.prolog.login.ui.autoceptor.AutoInterceptorPatternMaker;

@Configuration
@AllArgsConstructor
public class LoginConfig implements WebMvcConfigurer {
    private final static String BASE_PACKAGE = "wooteco.prolog";

    private final LoginInterceptor loginInterceptor;
    private final AuthMemberPrincipalArgumentResolver authMemberPrincipalArgumentResolver;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        AutoInterceptorPatternMaker mapper =
            new AutoInterceptorPatternMaker(BASE_PACKAGE, AuthMemberPrincipal.class);

        registry.addInterceptor(loginInterceptor)
            .addPathPatterns(mapper.extractPatterns());
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(authMemberPrincipalArgumentResolver);
    }
}
