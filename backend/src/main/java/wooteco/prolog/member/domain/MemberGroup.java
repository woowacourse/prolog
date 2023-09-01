package wooteco.prolog.member.domain;

import static wooteco.prolog.common.exception.BadRequestCode.CANT_FIND_GROUP_TYPE;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wooteco.prolog.common.exception.BadRequestCode;
import wooteco.prolog.common.exception.BadRequestException;

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

    public MemberGroupType groupType() {
        for (MemberGroupType groupType : MemberGroupType.values()) {
            if (groupType.isContainedBy(this.name)) {
                return groupType;
            }
        }
        throw new BadRequestException(CANT_FIND_GROUP_TYPE);
    }

}
