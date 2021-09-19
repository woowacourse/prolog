package wooteco.support.security.jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import wooteco.support.security.authentication.AuthenticationToken;

@AllArgsConstructor
@Getter
public class JwtAuthenticationToken implements AuthenticationToken {

    private String accessToken;

    @Override
    public Object getPrincipal() {
        return accessToken;
    }
}
