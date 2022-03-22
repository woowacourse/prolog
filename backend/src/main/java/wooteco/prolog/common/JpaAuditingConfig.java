package wooteco.prolog.common;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Profile("!docu")
@EnableJpaAuditing
@Configuration
public class JpaAuditingConfig {

}
