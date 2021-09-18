package wooteco.prolog.login.config;

import java.util.HashMap;
import java.util.Map;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import wooteco.prolog.login.config.OAuth2ClientProperties.Provider;
import wooteco.support.security.client.ClientRegistration;
import wooteco.support.security.client.ClientRegistrationRepository;
import wooteco.support.security.client.ProviderDetails;

@Configuration
@EnableConfigurationProperties(OAuth2ClientProperties.class)
public class ClientRegistrationRepositoryConfig {

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