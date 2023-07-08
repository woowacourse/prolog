package wooteco.prolog.member.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class MemberGroup {

    private static final String BACKEND = "백엔드";
    private static final String FRONTEND = "프론트엔드";
    private static final String ANDROID = "안드로이드";

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
