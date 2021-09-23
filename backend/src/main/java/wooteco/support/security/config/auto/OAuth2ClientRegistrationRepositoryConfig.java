package wooteco.support.security.config.auto;

import java.util.HashMap;
import java.util.Map;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import wooteco.support.security.config.auto.OAuth2ClientProperties.Provider;
import wooteco.support.security.oauth2.ClientRegistration;
import wooteco.support.security.oauth2.ClientRegistrationRepository;
import wooteco.support.security.oauth2.ProviderDetails;

@Configuration
@EnableConfigurationProperties(OAuth2ClientProperties.class)
public class OAuth2ClientRegistrationRepositoryConfig {

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository(
        OAuth2ClientProperties properties) {
        Map<String, ClientRegistration> clientRegistrations = new HashMap<>();
        properties.getRegistration().forEach((key, value) -> clientRegistrations.put(key,
            getClientRegistration(key, value, properties.getProvider())));

        return new ClientRegistrationRepository(clientRegistrations);
    }

    private static ClientRegistration getClientRegistration(String registrationId,
                                                            OAuth2ClientProperties.Registration properties,
                                                            Map<String, Provider> providers) {
        return new ClientRegistration(
            registrationId,
            properties.getClientId(),
            properties.getClientSecret(),
            new ProviderDetails(
                providers.get(registrationId).getTokenUri(),
                providers.get(registrationId).getUserInfoUri()
            )
        );
    }
}