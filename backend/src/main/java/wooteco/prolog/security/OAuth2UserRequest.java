package wooteco.prolog.security;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class OAuth2UserRequest {

    private String accessToken;
}
