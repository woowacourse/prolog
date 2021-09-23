package wooteco.support.security.config;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import wooteco.support.security.auto.ArgumentResolverConfig;
import wooteco.support.security.auto.SecurityFilterConfiguration;
import wooteco.support.security.auto.properties.JwtTokenPropertiesConfig;
import wooteco.support.security.auto.properties.OAuth2ClientRegistrationRepositoryConfig;

@Retention(value = java.lang.annotation.RetentionPolicy.RUNTIME)
@Target(value = {java.lang.annotation.ElementType.TYPE})
@Documented
@Import({WebSecurityConfiguration.class,
    SecurityFilterConfiguration.class,
    JwtTokenPropertiesConfig.class,
    OAuth2ClientRegistrationRepositoryConfig.class,
    ArgumentResolverConfig.class})
@Configuration
public @interface EnableWebSecurity {

}
