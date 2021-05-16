package wooteco.studylog.login;

public class TokenResponse {

    private String token;

    public TokenResponse() {
    }

    private TokenResponse(String token) {
        this.token = token;
    }

    public static TokenResponse of(String accessToken) {
        return new TokenResponse(accessToken);
    }

    public String getToken() {
        return token;
    }
}
