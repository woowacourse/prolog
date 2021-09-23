package wooteco.support.security.config;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import wooteco.support.security.auto.SecurityFilterConfiguration;

@Retention(value = java.lang.annotation.RetentionPolicy.RUNTIME)
@Target(value = {java.lang.annotation.ElementType.TYPE})
@Documented
@Import({WebSecurityConfiguration.class, SecurityFilterConfiguration.class})
@Configuration
public @interface EnableWebSecurity {

}
