package wooteco.prolog.member.domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class MemberGroupTest {

    @DisplayName("유효한 그룹명을 조회한다.")
    @CsvSource(value = {
        "5기 백엔드:5기 백엔드 설명:백엔드",
        "5기 프론트엔드:5기 프론트엔드 설명:프론트엔드",
        "5기 안드로이드:5기 안드로이드 설명:안드로이드"
    }, delimiter = ':')
    @ParameterizedTest(name = "이름으로 {0}을 가지고 설명으로 {1}을 가질 때 그룹 명으로 {2}를 반환한다.")
    void getGroupName(final String name, final String description, final String expected) {
        //given
        final MemberGroup memberGroup = new MemberGroup(1L, name, description);

        //when
        final String groupName = memberGroup.getGroupName();

        //then
        assertThat(groupName)
            .isEqualTo(expected);
    }

    @DisplayName("이름에 그룹명을 칭하는 문자열이 없을 경우 null 을 반환한다.")
    @Test
    void getGroupNameNull() {
        //given
        final MemberGroup memberGroup = new MemberGroup(1L, "5기 기타그룹", "5기 기타그룹 설명");

        //when
        final String groupName = memberGroup.getGroupName();

        //then
        assertThat(groupName)
            .isNull();
    }
}
