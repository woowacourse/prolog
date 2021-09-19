package wooteco.support.security.oauth2user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import wooteco.support.security.authentication.AuthenticationRequest;
import wooteco.support.security.authentication.oauth2.OAuth2AuthorizationExchange;
import wooteco.support.security.client.ClientRegistration;

@AllArgsConstructor
@Getter
public class OAuth2AuthorizationRequest implements AuthenticationRequest {

    private final ClientRegistration clientRegistration;
    private final OAuth2AuthorizationExchange exchange;
}
