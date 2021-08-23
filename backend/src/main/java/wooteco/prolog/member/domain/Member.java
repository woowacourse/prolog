package wooteco.prolog.member.domain;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.ObjectUtils;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column
    private String nickname;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(nullable = false, unique = true)
    private Long githubId;

    @Column(nullable = false)
    private String imageUrl;

    public Member(String username, String nickname, Role role, Long githubId, String imageUrl) {
        this(null, username, nickname, role, githubId, imageUrl);
    }

    public Member(Long id,
        String username,
        String nickname,
        Role role,
        Long githubId,
        String imageUrl)
    {
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

    public void updateNickname(String nickname) {
        if (!ObjectUtils.isEmpty(nickname)) {
            this.nickname = nickname;
        }
    }

    public void updateImageUrl(String imageUrl) {
        if (!ObjectUtils.isEmpty(imageUrl)) {
            this.imageUrl = imageUrl;
        }
    }

    public Long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Member)) {
            return false;
        }
        Member member = (Member) o;
        return Objects.equals(id, member.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
