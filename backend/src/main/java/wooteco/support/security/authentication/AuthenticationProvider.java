package wooteco.support.security.authentication;

import lombok.AllArgsConstructor;
import wooteco.support.security.oauth2user.OAuth2AccessTokenResponse;
import wooteco.support.security.oauth2user.OAuth2AuthorizationGrantRequest;
import wooteco.support.security.oauth2user.OAuth2User;
import wooteco.support.security.oauth2user.OAuth2UserRequest;
import wooteco.support.security.oauth2user.OAuth2UserService;

@AllArgsConstructor
public class AuthenticationProvider {

    private final OAuth2AccessTokenResponseClient tokenResponseClient;
    private final OAuth2UserService oAuth2UserService;

    public OAuth2Authentication authenticate(OAuth2AuthorizationGrantRequest grantRequest) {
        OAuth2AccessTokenResponse githubAccessToken = tokenResponseClient
            .getTokenResponse(grantRequest);

        OAuth2UserRequest oAuth2UserRequest =
            new OAuth2UserRequest(
                grantRequest.getClientRegistration(),
                githubAccessToken.getAccessToken()
            );

        OAuth2User oauth2User = oAuth2UserService.loadUser(oAuth2UserRequest);

        return new OAuth2Authentication(oauth2User, grantRequest.getClientRegistration());
    }
}
