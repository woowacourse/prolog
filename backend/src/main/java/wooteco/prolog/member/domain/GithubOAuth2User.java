package wooteco.prolog.member.domain;

import java.util.Map;
import lombok.AllArgsConstructor;
import wooteco.prolog.security.Authentication;

@AllArgsConstructor
public class GithubOAuth2User implements Authentication {

    private Map<String, Object> userInfo;

    public String getLoginName() {
        return userInfo.get("name").toString();
    }

    public String getNickname() {
        return userInfo.get("login").toString();
    }

    public Long getGithubId() {
        return Long.parseLong(userInfo.get("id").toString());
    }

    public String getImageUrl() {
        return userInfo.get("avatar_url").toString();
    }

    public Member toMember() {
        return new Member(
            getLoginName(),
            getNickname(),
            Role.CREW,
            getGithubId(),
            getImageUrl()
        );
    }

    @Override
    public Object getPrincipal() {
        return userInfo;
    }
}
