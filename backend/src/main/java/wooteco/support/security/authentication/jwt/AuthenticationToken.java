package wooteco.support.security.authentication.jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import wooteco.support.security.authentication.Authentication;

@AllArgsConstructor
@Getter
public class AuthenticationToken implements Authentication {

    private Object principal;
}
