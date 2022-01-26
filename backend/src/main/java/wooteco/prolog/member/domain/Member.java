package wooteco.prolog.member.domain;

import static wooteco.prolog.member.domain.Role.UNVALIDATED;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.persistence.Column;
import javax.persistence.Embedded;
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

    @Column
    private String profileIntro;

    @Embedded
    private MemberTags memberTags;

    public Member(String username, String nickname, Role role, Long githubId, String imageUrl) {
        this(null, username, nickname, role, githubId, imageUrl);
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
        if (!ObjectUtils.isEmpty(text)) {
            this.profileIntro = text;
        }
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

    public boolean isAnonymous() {
        return this.role == UNVALIDATED;
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
        return Objects.equals(id, member.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
