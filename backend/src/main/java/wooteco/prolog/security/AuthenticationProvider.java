package wooteco.prolog.security;

import lombok.AllArgsConstructor;
import wooteco.prolog.login.application.OAuth2AccessTokenResponseClient;
import wooteco.prolog.login.application.dto.OAuth2AuthorizationGrantRequest;

@AllArgsConstructor
public class AuthenticationProvider {

    private final OAuth2AccessTokenResponseClient tokenResponseClient;

    public OAuth2AccessTokenResponse authenticate(OAuth2AuthorizationGrantRequest grantRequest) {
        OAuth2AccessTokenResponse githubAccessToken =
            tokenResponseClient.getTokenResponse(grantRequest.getCode());

        return githubAccessToken;
    }
}
