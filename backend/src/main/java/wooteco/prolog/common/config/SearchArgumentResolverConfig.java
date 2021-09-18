package wooteco.prolog.common.config;

import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.config.PageableHandlerMethodArgumentResolverCustomizer;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import wooteco.prolog.studylog.application.dto.search.SearchArgumentResolver;

@Configuration
@AllArgsConstructor
public class SearchArgumentResolverConfig implements WebMvcConfigurer {

    private SearchArgumentResolver searchArgumentResolver;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedMethods("*").allowedOriginPatterns("*");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(searchArgumentResolver);
    }

    @Bean
    public PageableHandlerMethodArgumentResolverCustomizer customize() {
        return page -> page.setOneIndexedParameters(true);
    }
}
