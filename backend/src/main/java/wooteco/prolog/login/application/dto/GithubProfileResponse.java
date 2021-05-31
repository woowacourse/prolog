package wooteco.prolog.login.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GithubProfileResponse {

    @JsonProperty("name")
    private String nickname;
    @JsonProperty("id")
    private String githubId;
    @JsonProperty("avatar_url")
    private String imageUrl;

    public GithubProfileResponse() {

    }

    public GithubProfileResponse(String nickname, String githubId, String imageUrl) {
        this.nickname = nickname;
        this.githubId = githubId;
        this.imageUrl = imageUrl;
    }

    public String getNickname() {
        return nickname;
    }

    public Long getGithubId() {
        return Long.valueOf(githubId);
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
