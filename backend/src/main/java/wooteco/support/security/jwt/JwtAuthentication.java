package wooteco.support.security.jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import wooteco.support.security.authentication.Authentication;

@AllArgsConstructor
@Getter
public class JwtAuthentication implements Authentication {

    private String username;

    @Override
    public Object getPrincipal() {
        return username;
    }
}
