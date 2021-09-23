package wooteco.support.security.config.auto;

import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import wooteco.support.security.core.AuthenticationPrincipalArgumentResolver;

@Configuration
public class ArgumentResolverConfig implements WebMvcConfigurer {

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(authMemberPrincipalArgumentResolver());
    }

    private AuthenticationPrincipalArgumentResolver authMemberPrincipalArgumentResolver() {
        return new AuthenticationPrincipalArgumentResolver();
    }
}
