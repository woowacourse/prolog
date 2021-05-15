package wooteco.studylog.login;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GithubProfileResponse {
    private String login;
    private String id;
    @JsonProperty("avatar_url")
    private String avatarUrl;

    public GithubProfileResponse() {
    }

    public GithubProfileResponse(String login, String id, String avatarUrl) {
        this.login = login;
        this.id = id;
        this.avatarUrl = avatarUrl;
    }

    public String getLogin() {
        return login;
    }

    public String getId() {
        return id;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }
}
