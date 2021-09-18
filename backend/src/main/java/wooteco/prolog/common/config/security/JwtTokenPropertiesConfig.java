package wooteco.prolog.common.config.security;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import wooteco.support.security.authentication.jwt.JwtTokenProvider;

@Configuration
@EnableConfigurationProperties(JwtTokenProperties.class)
public class JwtTokenPropertiesConfig {

    @Bean
    public JwtTokenProvider jwtTokenProvider(JwtTokenProperties properties) {
        return new JwtTokenProvider(properties.getSecretKey(), properties.getExpireLength());
    }
}
