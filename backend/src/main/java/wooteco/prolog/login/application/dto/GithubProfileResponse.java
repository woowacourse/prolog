package wooteco.prolog.login.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import wooteco.prolog.login.domain.Member;
import wooteco.prolog.login.domain.Role;

@AllArgsConstructor
@NoArgsConstructor
public class GithubProfileResponse {

    @JsonProperty("name")
    private String nickname;
    @JsonProperty("login")
    private String loginName;
    @JsonProperty("id")
    private String githubId;
    @JsonProperty("avatar_url")
    private String imageUrl;

    public String getLoginName() {
        return loginName;
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

    public Member toMember() {
        return new Member(
                getNickname(),
                getLoginName(),
                Role.CREW,
                getGithubId(),
                getImageUrl()
        );
    }
}
