package wooteco.support.security.oauth2user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import wooteco.support.security.authentication.OAuth2AuthorizationExchange;
import wooteco.support.security.client.ClientRegistration;

@AllArgsConstructor
@Getter
public class OAuth2AuthorizationGrantRequest {

    private final ClientRegistration clientRegistration;
    private final OAuth2AuthorizationExchange exchange;
}
