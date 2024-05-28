package wooteco.prolog.common;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import wooteco.prolog.article.application.SSLUtil;

@Configuration
public class SSLConfig {

    @Bean
    @Profile({"local", "test"})
    public ApplicationRunner applicationRunner() {
        return args -> SSLUtil.disableSSLVerification();
    }
}
