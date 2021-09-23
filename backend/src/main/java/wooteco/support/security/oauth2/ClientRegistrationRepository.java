package wooteco.support.security.oauth2;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.util.Assert;

@AllArgsConstructor
@Getter
public class ClientRegistrationRepository {

    private final Map<String, ClientRegistration> registrations;

    public ClientRegistration findByRegistrationId(String registrationId) {
        Assert.hasText(registrationId, "registrationId cannot be empty");
        return this.registrations.get(registrationId);
    }
}
