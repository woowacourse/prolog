package wooteco.prolog.security;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class OAuth2AccessTokenResponse implements Authentication {

    private String accessToken;
    private String refreshToken;

    @Override
    public Object getPrincipal() {
        return accessToken;
    }
}
