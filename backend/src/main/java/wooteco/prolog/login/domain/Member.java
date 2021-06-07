package wooteco.prolog.login.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import wooteco.prolog.login.application.dto.GithubProfileResponse;

@Getter
@EqualsAndHashCode(of = "id")
public class Member {

    private final Long id;
    private final String nickname;
    private final Role role;
    private final Long githubId;
    private final String imageUrl;

    public Member(String nickname, Role role, Long githubId, String imageUrl) {
        this(null, nickname, role, githubId, imageUrl);
    }

    public Member(Long id, String nickname, Role role, Long githubId, String imageUrl) {
        this.id = id;
        this.nickname = nickname;
        this.role = role;
        this.githubId = githubId;
        this.imageUrl = imageUrl;
    }

    public static Member of(GithubProfileResponse githubProfile) {
        return new Member(
                githubProfile.getNickname(),
                Role.CREW,
                githubProfile.getGithubId(),
                githubProfile.getImageUrl()
        );
    }

}
