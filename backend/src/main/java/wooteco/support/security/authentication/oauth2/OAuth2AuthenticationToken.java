package wooteco.support.security.authentication.oauth2;

import lombok.AllArgsConstructor;
import lombok.Getter;
import wooteco.support.security.authentication.Authentication;
import wooteco.support.security.client.ClientRegistration;
import wooteco.support.security.oauth2user.OAuth2User;

@AllArgsConstructor
@Getter
public class OAuth2AuthenticationToken implements Authentication {

    private OAuth2User principal;
    private ClientRegistration clientRegistration;

}
