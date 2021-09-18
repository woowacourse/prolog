package wooteco.prolog.login.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "security.jwt.token")
public class JwtTokenProperties {

    private String secretKey;
    private long expireLength;

}
