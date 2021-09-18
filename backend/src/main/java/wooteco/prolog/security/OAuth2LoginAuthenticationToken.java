package wooteco.prolog.security;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class OAuth2LoginAuthenticationToken implements Authentication {

    private OAuth2User principal;

}
