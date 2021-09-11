package wooteco.prolog.member.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.ObjectUtils;
import wooteco.prolog.studylog.domain.ablity.Ability;
import wooteco.prolog.studylog.exception.AbilityHasChildrenException;
import wooteco.prolog.studylog.exception.AbilityNotFoundException;

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

    @OneToMany(mappedBy = "member", orphanRemoval = true)
    private List<Ability> abilities;

    public Member(String username, String nickname, Role role, Long githubId, String imageUrl) {
        this(null, username, nickname, role, githubId, imageUrl);
    }

    public Member(Long id,
                  String username,
                  String nickname,
                  Role role,
                  Long githubId,
                  String imageUrl) {
        this.id = id;
        this.username = username;
        this.nickname = ifAbsentReplace(nickname, username);
        this.role = role;
        this.githubId = githubId;
        this.imageUrl = imageUrl;
        this.abilities = new ArrayList<>();
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

    public void addAbility(Ability ability) {
        abilities.add(ability);
    }

    public void updateAbility(Ability ability) {
        Ability targetAbility = abilities.stream()
            .filter(target -> target.equals(ability))
            .findAny()
            .orElseThrow(AbilityNotFoundException::new);

        targetAbility.update(ability);
    }

    public void deleteAbility(Ability ability) {
        if (!abilities.contains(ability)) {
            throw new AbilityNotFoundException();
        }

        if (ability.hasChildren()) {
            throw new AbilityHasChildrenException();
        }

        abilities.remove(ability);
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
