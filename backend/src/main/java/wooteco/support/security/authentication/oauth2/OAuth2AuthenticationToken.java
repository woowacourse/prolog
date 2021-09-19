package wooteco.support.security.authentication.oauth2;

import lombok.AllArgsConstructor;
import lombok.Getter;
import wooteco.support.security.authentication.AuthenticationToken;
import wooteco.support.security.authentication.oauth2.OAuth2AuthorizationExchange;
import wooteco.support.security.client.ClientRegistration;

@AllArgsConstructor
@Getter
public class OAuth2AuthenticationToken implements AuthenticationToken {

    private final ClientRegistration clientRegistration;
    private final OAuth2AuthorizationExchange exchange;

    @Override
    public Object getPrincipal() {
        return exchange;
    }
}
