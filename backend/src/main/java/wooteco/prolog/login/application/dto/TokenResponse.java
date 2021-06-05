package wooteco.prolog.login.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class TokenResponse {

    private String accessToken;

    public static TokenResponse of(String accessToken) {
        return new TokenResponse(accessToken);
    }

}
