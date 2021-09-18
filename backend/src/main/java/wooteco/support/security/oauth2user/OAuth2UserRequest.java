package wooteco.support.security.oauth2user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import wooteco.support.security.client.ClientRegistration;

@AllArgsConstructor
@Getter
public class OAuth2UserRequest {

    private final ClientRegistration clientRegistration;
    private final String accessToken;
}
