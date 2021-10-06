package wooteco.prolog.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.config.PageableHandlerMethodArgumentResolverCustomizer;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import wooteco.prolog.studylog.application.dto.search.SearchArgumentResolver;
import wooteco.support.performance.PerformanceLogger;
import wooteco.support.performance.RequestApiExtractor;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
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

    @Bean
    public PerformanceLogger performanceLogger(ObjectMapper objectMapper) {
        return new PerformanceLogger(objectMapper, new RequestApiExtractor());
    }
}
