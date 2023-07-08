package wooteco.prolog.member.domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

class MemberGroupTypeTest {

    @ParameterizedTest
    @EnumSource(value = MemberGroupType.class)
    void isContainedBy_null은_그룹명을_포함하지_않는다(MemberGroupType memberGroupType) {
        assertThat(memberGroupType.isContainedBy(null)).isFalse();
    }

    @Test
    void isContainedBy() {
        assertThat(MemberGroupType.ANDROID.isContainedBy("안드로이드 5기")).isTrue();
        assertThat(MemberGroupType.ANDROID.isContainedBy("프론트엔드 5기")).isFalse();
        assertThat(MemberGroupType.ANDROID.isContainedBy("백엔드 5기")).isFalse();

        assertThat(MemberGroupType.FRONTEND.isContainedBy("안드로이드 5기")).isFalse();
        assertThat(MemberGroupType.FRONTEND.isContainedBy("프론트엔드 5기")).isTrue();
        assertThat(MemberGroupType.FRONTEND.isContainedBy("백엔드 5기")).isFalse();

        assertThat(MemberGroupType.BACKEND.isContainedBy("안드로이드 5기")).isFalse();
        assertThat(MemberGroupType.BACKEND.isContainedBy("프론트엔드 5기")).isFalse();
        assertThat(MemberGroupType.BACKEND.isContainedBy("백엔드 5기")).isTrue();
    }
}
