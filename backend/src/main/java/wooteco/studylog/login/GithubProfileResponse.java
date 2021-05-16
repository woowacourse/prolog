package wooteco.studylog.login;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GithubProfileResponse {

    @JsonProperty("name")
    private String nickname;
    @JsonProperty("id")
    private String githubId;
    @JsonProperty("avatar_url")
    private String image;

    public GithubProfileResponse() {

    }

    public GithubProfileResponse(String nickname, String githubId, String image) {
        this.nickname = nickname;
        this.githubId = githubId;
        this.image = image;
    }

    public String getNickname() {
        return nickname;
    }

    public String getGithubId() {
        return githubId;
    }

    public String getImage() {
        return image;
    }
}
