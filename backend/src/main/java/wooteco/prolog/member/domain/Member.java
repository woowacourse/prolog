package wooteco.prolog.member.domain;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.ObjectUtils;
import wooteco.prolog.BaseEntity;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Member extends BaseEntity {

    @Column
    private String username;

    @Column
    private String nickname;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column
    private Long githubId;

    @Column
    private String imageUrl;

    public Member(String username, String nickname, Role role, Long githubId, String imageUrl) {
        this(null, username, nickname, role, githubId, imageUrl);
    }

    public Member(Long id, String username, String nickname, Role role, Long githubId, String imageUrl) {
        super(id);
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

    public void update(String nickname, String imageUrl) {
        if (!ObjectUtils.isEmpty(nickname)) {
            this.nickname = nickname;
        }
        if (!ObjectUtils.isEmpty(imageUrl)) {
            this.imageUrl = imageUrl;
        }
    }

    public boolean isNotAuthor(Member member){
        return !this.equals(member);
    }
}
