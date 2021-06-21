package wooteco.prolog.login.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import wooteco.prolog.login.application.dto.GithubProfileResponse;

import java.util.Objects;

@Getter
@EqualsAndHashCode(of = "id")
public class Member {

    private final Long id;
    private final String nickname;
    private final String loginName;
    private final Role role;
    private final Long githubId;
    private final String imageUrl;

    public Member(String nickname, String loginName, Role role, Long githubId, String imageUrl) {
        this(null, nickname, loginName, role, githubId, imageUrl);
    }

    public Member(Long id, String nickname, String loginName, Role role, Long githubId, String imageUrl) {
        this.id = id;
        this.nickname = ifAbsentReplace(nickname, loginName);
        this.loginName = loginName;
        this.role = role;
        this.githubId = githubId;
        this.imageUrl = imageUrl;
    }

    private String ifAbsentReplace(String nickname, String loginName) {
        if (Objects.isNull(nickname) || nickname.isEmpty()) {
            return loginName;
        }
        return nickname;
    }

    public static Member of(GithubProfileResponse githubProfile) {
        return new Member(
                githubProfile.getNickname(),
                githubProfile.getLoginName(),
                Role.CREW,
                githubProfile.getGithubId(),
                githubProfile.getImageUrl()
        );
    }

}
