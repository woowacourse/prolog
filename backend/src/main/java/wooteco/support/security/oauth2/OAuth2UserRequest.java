package wooteco.support.security.oauth2;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class OAuth2UserRequest {

    private final ClientRegistration clientRegistration;
    private final String accessToken;
}
