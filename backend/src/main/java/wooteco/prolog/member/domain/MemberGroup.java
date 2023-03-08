package wooteco.prolog.member.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class MemberGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    public MemberGroup(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public MemberGroupType getGroupType() {
        for (MemberGroupType groupType : MemberGroupType.values()) {
            if (groupType.isContainedBy(this.name)) {
                return groupType;
            }
        }
        throw new IllegalArgumentException("그룹이 포함되는 타입이 없습니다. id=" + this.id);
    }
}
