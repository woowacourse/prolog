package wooteco.prolog.member.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.ObjectUtils;

import java.util.Objects;

@NoArgsConstructor
@Getter
@EqualsAndHashCode(of = "id")
public class Member {

    private Long id;
    private String username;
    private String nickname;
    private Role role;
    private Long githubId;
    private String imageUrl;

    public Member(String username, String nickname, Role role, Long githubId, String imageUrl) {
        this(null, username, nickname, role, githubId, imageUrl);
    }

    public Member(Long id, String username, String nickname, Role role, Long githubId, String imageUrl) {
        this.id = id;
        this.username = username;
        this.nickname = ifAbsentReplace(nickname, username);
        this.role = role;
        this.githubId = githubId;
        this.imageUrl = imageUrl;
    }

    private String ifAbsentReplace(String nickname, String username) {
        if (Objects.isNull(nickname) || nickname.isEmpty()) {
            return username;
        }
        return nickname;
    }

    public void update(String username, String nickname, String imageUrl) {
        if (!ObjectUtils.isEmpty(username)) {
            this.username = username;
        }
        if (!ObjectUtils.isEmpty(nickname)) {
            this.nickname = nickname;
        }
        if (!ObjectUtils.isEmpty(imageUrl)) {
            this.imageUrl = imageUrl;
        }
    }
}
