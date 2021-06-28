package wooteco.prolog.login.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wooteco.prolog.login.application.dto.GithubProfileResponse;

import java.util.Objects;

@NoArgsConstructor
@Getter
@EqualsAndHashCode(of = "id")
public class Member {

    private Long id;
    private String nickname;
    private String githubUserName;
    private Role role;
    private Long githubId;
    private String imageUrl;

    public Member(String nickname, String githubUserName, Role role, Long githubId, String imageUrl) {
        this(null, nickname, githubUserName, role, githubId, imageUrl);
    }

    public Member(Long id, String nickname, String githubUserName, Role role, Long githubId, String imageUrl) {
        this.id = id;
        this.nickname = ifAbsentReplace(nickname, githubUserName);
        this.githubUserName = githubUserName;
        this.role = role;
        this.githubId = githubId;
        this.imageUrl = imageUrl;
    }

    private String ifAbsentReplace(String nickname, String githubUserName) {
        if (Objects.isNull(nickname) || nickname.isEmpty()) {
            return githubUserName;
        }
        return nickname;
    }

}
