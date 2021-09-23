package wooteco.support.security.oauth2;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ClientRegistration {

    private String registrationId;
    private String clientId;
    private String clientSecret;
    private ProviderDetails providerDetails;
}
