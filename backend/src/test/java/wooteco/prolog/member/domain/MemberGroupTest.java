package wooteco.prolog.member.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
class MemberGroupTest {

    @DisplayName("이름에 백엔드 문자열을 포함할 경우 백엔드 그룹명을 반환한다.")
    @Test
    void getGroupNameBackEnd(){
        MemberGroup memberGroup = new MemberGroup(1L, "5기 백엔드", "5기 백엔드 설명");
        String groupName = memberGroup.getGroupName();
        assertThat(groupName)
            .isEqualTo("백엔드");
    }

    @DisplayName("이름에 프론트엔드 문자열을 포함할 경우 프론트엔드 그룹명을 반환한다.")
    @Test
    void getGroupNameFrontEnd(){
        MemberGroup memberGroup = new MemberGroup(1L, "5기 프론트엔드", "5기 프론트엔드 설명");
        String groupName = memberGroup.getGroupName();
        assertThat(groupName)
            .isEqualTo("프론트엔드");
    }

    @DisplayName("이름에 안드로이드 문자열을 포함할 경우 안드로이드 그룹명을 반환한다.")
    @Test
    void getGroupNameAndroid(){
        MemberGroup memberGroup = new MemberGroup(1L, "5기 안드로이드", "5기 안드로이드 설명");
        String groupName = memberGroup.getGroupName();
        assertThat(groupName)
            .isEqualTo("안드로이드");
    }

    @DisplayName("이름에 그룹명을 칭하는 문자열이 없을 경우 null 을 반환한다.")
    @Test
    void getGroupNameNull(){
        MemberGroup memberGroup = new MemberGroup(1L, "5기 기타그룹", "5기 기타그룹 설명");
        String groupName = memberGroup.getGroupName();
        assertThat(groupName)
            .isNull();
    }
}
