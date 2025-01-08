package wooteco.prolog.member.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.ObjectUtils;
import wooteco.prolog.studylog.domain.Tag;
import wooteco.prolog.studylog.domain.Tags;

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

    @Column(columnDefinition = "text")
    private String profileIntro;

    @Embedded
    private MemberTags memberTags;

    @Column
    private String rssFeedUrl;

    public Member(String username, String nickname, Role role, Long githubId, String imageUrl) {
        this(null, username, nickname, role, githubId, imageUrl);
    }

    public Member(String username, String nickname, Role role, Long githubId, String imageUrl, String rssFeedUrl) {
        this(null, username, nickname, role, githubId, imageUrl, null, rssFeedUrl);
    }

    public Member(Long id,
                  String username,
                  String nickname,
                  Role role,
                  Long githubId,
                  String imageUrl) {
        this(id, username, nickname, role, githubId, imageUrl, new MemberTags());
    }

    public Member(Long id,
                  String username,
                  String nickname,
                  Role role,
                  Long githubId,
                  String imageUrl,
                  MemberTags memberTags) {
        this.id = id;
        this.username = username;
        this.nickname = ifAbsentReplace(nickname, username);
        this.role = role;
        this.githubId = githubId;
        this.imageUrl = imageUrl;
        this.memberTags = memberTags;
    }

    public Member(Long id,
                  String username,
                  String nickname,
                  Role role,
                  Long githubId,
                  String imageUrl,
                  MemberTags memberTags,
                  String rssFeedUrl) {
        this.id = id;
        this.username = username;
        this.nickname = ifAbsentReplace(nickname, username);
        this.role = role;
        this.githubId = githubId;
        this.imageUrl = imageUrl;
        this.memberTags = memberTags;
        this.rssFeedUrl = rssFeedUrl;
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

    public void updateProfileIntro(String text) {
        this.profileIntro = text;
    }

    public void updateRssFeedUrl(String url) {
        this.rssFeedUrl = url;
    }

    public void addTag(Tag tag) {
        memberTags.add(new MemberTag(this, tag));
    }

    public void addTags(Tags tags) {
        memberTags.addMemberTags(toMemberTag(tags));
    }

    public Long getId() {
        return id;
    }

    public void updateTags(Tags originalTags, Tags newTags) {
        memberTags.updateTags(toMemberTag(originalTags), toMemberTag(newTags));
    }

    public void removeTag(Tags tags) {
        memberTags.removeMemberTags(toMemberTag(tags));
    }

    private List<MemberTag> toMemberTag(Tags tags) {
        return tags.getList().stream()
            .map(tag -> new MemberTag(this, tag))
            .collect(Collectors.toList());
    }

    public List<MemberTag> getMemberTagsWithSort() {
        return memberTags.getValues()
            .stream()
            .sorted((o1, o2) -> o2.getCount() - o1.getCount())
            .collect(Collectors.toList());
    }

    public boolean hasLowerImportanceRoleThan(final Role role) {
        return this.role.hasLowerImportanceThan(role);
    }

    public void updateRole(final Role role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Member member)) {
            return false;
        }
        return Objects.equals(id, member.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
