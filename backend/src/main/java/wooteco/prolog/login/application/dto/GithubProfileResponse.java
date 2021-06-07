package wooteco.prolog.login.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class GithubProfileResponse {

    @JsonProperty("name")
    private String nickname;
    @JsonProperty("id")
    private String githubId;
    @JsonProperty("avatar_url")
    private String imageUrl;

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
