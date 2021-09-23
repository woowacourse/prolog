package wooteco.support.security.config.auto;

import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "security.oauth2.client")
public class OAuth2ClientProperties {

    private final Map<String, Provider> provider = new HashMap<>();
    private final Map<String, Registration> registration = new HashMap<>();

    @Getter
    @Setter
    public static class Registration {

        private String provider;
        private String clientId;
        private String clientSecret;
    }

    @Getter
    @Setter
    public static class Provider {

        private String tokenUri;
        private String userInfoUri;
    }
}
