package wooteco.prolog.login.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GithubAccessTokenResponse {

    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("token_type")
    private String tokenType;
    private String scope;
    private String bearer;

    public GithubAccessTokenResponse() {

    }

    public GithubAccessTokenResponse(String accessToken, String tokenType, String scope, String bearer) {
        this.accessToken = accessToken;
        this.tokenType = tokenType;
        this.scope = scope;
        this.bearer = bearer;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public String getScope() {
        return scope;
    }

    public String getBearer() {
        return bearer;
    }
}
