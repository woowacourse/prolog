package wooteco.support.security.oauth2user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class OAuth2AccessTokenResponse {

    private String accessToken;
    private String refreshToken;

}
