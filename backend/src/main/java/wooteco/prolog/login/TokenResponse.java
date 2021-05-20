package wooteco.prolog.login;

public class TokenResponse {

    private String accessToken;

    public TokenResponse() {
    }

    private TokenResponse(String accessToken) {
        this.accessToken = accessToken;
    }

    public static TokenResponse of(String accessToken) {
        return new TokenResponse(accessToken);
    }

    public String getAccessToken() {
        return accessToken;
    }

}
