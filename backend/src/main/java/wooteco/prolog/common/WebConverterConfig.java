package wooteco.prolog.common;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import wooteco.prolog.article.domain.ArticleFilterType;

@Configuration
public class WebConverterConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(String.class, Month.class,
            source -> Month.of(Integer.parseInt(source))
        );
        registry.addConverter(String.class, LocalDate.class,
            source -> LocalDate.parse(source, DateTimeFormatter.BASIC_ISO_DATE)
        );

        registry.addConverter(String.class, ArticleFilterType.class,
            source -> ArticleFilterType.valueOf(source.toUpperCase())
        );
    }
}
