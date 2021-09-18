package wooteco.prolog.security;

import lombok.AllArgsConstructor;
import wooteco.prolog.login.application.OAuth2AccessTokenResponseClient;
import wooteco.prolog.login.application.dto.OAuth2AuthorizationGrantRequest;

@AllArgsConstructor
public class AuthenticationProvider {

    private final OAuth2AccessTokenResponseClient tokenResponseClient;
    private final OAuth2UserService oAuth2UserService;

    public Authentication authenticate(OAuth2AuthorizationGrantRequest grantRequest) {
        OAuth2AccessTokenResponse githubAccessToken = tokenResponseClient
            .getTokenResponse(grantRequest.getCode());

        OAuth2User oauth2User = oAuth2UserService
            .loadUser(new OAuth2UserRequest(githubAccessToken.getAccessToken()));

        return new OAuth2LoginAuthenticationToken(oauth2User);
    }
}
