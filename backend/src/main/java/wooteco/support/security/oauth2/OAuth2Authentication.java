package wooteco.support.security.oauth2;

import lombok.AllArgsConstructor;
import lombok.Getter;
import wooteco.support.security.authentication.Authentication;

@AllArgsConstructor
@Getter
public class OAuth2Authentication implements Authentication {

    private OAuth2User principal;
    private ClientRegistration clientRegistration;

}
