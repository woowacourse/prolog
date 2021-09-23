package wooteco.prolog.member.domain;

import java.util.Map;
import wooteco.support.security.oauth2.OAuth2User;

public class GithubOAuth2User extends OAuth2User {

    public GithubOAuth2User(Map<String, Object> attributes) {
        super(attributes);
    }

    public String getLoginName() {
        return getAttributes().get("login").toString();
    }

    public String getNickname() {
        return getAttributes().get("name").toString();
    }

    public Long getGithubId() {
        return Long.parseLong(getAttributes().get("id").toString());
    }

    public String getImageUrl() {
        return getAttributes().get("avatar_url").toString();
    }
}
