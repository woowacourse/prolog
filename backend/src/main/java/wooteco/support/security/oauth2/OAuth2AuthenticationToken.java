package wooteco.support.security.oauth2;

import lombok.AllArgsConstructor;
import lombok.Getter;
import wooteco.support.security.authentication.AuthenticationToken;

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
