package wooteco.support.security.authentication.oauth2;

import lombok.AllArgsConstructor;
import wooteco.support.security.authentication.Authentication;
import wooteco.support.security.authentication.AuthenticationProvider;
import wooteco.support.security.authentication.AuthenticationToken;
import wooteco.support.security.oauth2user.OAuth2AccessTokenResponse;
import wooteco.support.security.oauth2user.OAuth2User;
import wooteco.support.security.oauth2user.OAuth2UserRequest;
import wooteco.support.security.oauth2user.OAuth2UserService;

@AllArgsConstructor
public class OAuth2AuthenticationProvider implements AuthenticationProvider {

    private final OAuth2AccessTokenResponseClient tokenResponseClient;
    private final OAuth2UserService oAuth2UserService;

    @Override
    public Authentication authenticate(AuthenticationToken authenticationToken) {
        OAuth2AuthenticationToken grantRequest = (OAuth2AuthenticationToken) authenticationToken;
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

    @Override
    public boolean supports(Class<?> authentication) {
        return OAuth2AuthenticationToken.class.isAssignableFrom(authentication);
    }
}
