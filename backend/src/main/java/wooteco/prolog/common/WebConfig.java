package wooteco.prolog.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.web.config.PageableHandlerMethodArgumentResolverCustomizer;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import wooteco.prolog.common.slacklogger.RequestStorage;
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

    @Bean
    public FlywayMigrationStrategy cleanMigrationStrategy() {
        return flyway -> {
            flyway.clean();
            flyway.repair();
            flyway.migrate();
        };
    }

    @Bean
    @Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
    public RequestStorage requestStorage() {
        return new RequestStorage();
    }
}
