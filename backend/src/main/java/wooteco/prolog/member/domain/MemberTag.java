package wooteco.prolog.member.domain;

import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wooteco.prolog.studylog.domain.Tag;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "member_tag")
public class MemberTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id", nullable = false)
    private Tag tag;

    private int count;

    public MemberTag(Member member, Tag tag) {
        this(null, member, tag, 1);
    }

    public MemberTag(Long id, Member member, Tag tag, int count) {
        this.id = id;
        this.member = member;
        this.tag = tag;
        this.count = count;
    }

    public void addCount() {
        ++count;
    }

    public void decreaseCount() {
        --count;
    }

    public boolean hasOnlyOne() {
        return count == 1;
    }

    public boolean isSame(MemberTag memberTag) {
        return member.equals(memberTag.getMember()) && tag.isSameName(memberTag.tag);
    }

    public Long getMemberId() {
        return member.getId();
    }

    public Long getTagId() {
        return tag.getId();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MemberTag memberTag = (MemberTag) o;
        return Objects.equals(getMemberId(), memberTag.getMemberId()) &&
            Objects.equals(getTagId(), memberTag.getTagId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTagId(), getMemberId());
    }
}
