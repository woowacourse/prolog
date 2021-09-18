package wooteco.support.security.authentication;

import lombok.AllArgsConstructor;
import lombok.Getter;
import wooteco.support.security.client.ClientRegistration;
import wooteco.support.security.oauth2user.OAuth2User;

@AllArgsConstructor
@Getter
public class Authentication {

    private OAuth2User principal;
    private ClientRegistration clientRegistration;

}
