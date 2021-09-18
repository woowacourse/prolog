package wooteco.prolog.security;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Authentication {

    private OAuth2User principal;

}
