package wooteco.prolog;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Profile("!docu")
@Configuration
@EnableJpaAuditing
public class JpaAuditingConfig {

}