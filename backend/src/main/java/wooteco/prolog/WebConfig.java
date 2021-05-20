package wooteco.prolog;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        String[] allowedOriginPatterns = Arrays.asList(
                "http://localhost:3000"
        ).toArray(new String[0]);
        registry.addMapping("/**").allowedMethods("*").allowedOriginPatterns(allowedOriginPatterns);
    }
}
