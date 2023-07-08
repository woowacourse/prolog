package wooteco.prolog.member.domain;

import lombok.Getter;

@Getter
public enum MemberGroupType {
    ANDROID("안드로이드"),
    BACKEND("백엔드"),
    FRONTEND("프론트엔드");

    private final String groupName;

    MemberGroupType(String groupName) {
        this.groupName = groupName;
    }

    public boolean isContainedBy(String value) {
        if (value == null) {
            return false;
        }
        return value.contains(this.groupName);
    }
}
