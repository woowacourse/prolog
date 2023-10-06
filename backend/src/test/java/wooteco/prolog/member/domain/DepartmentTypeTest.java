package wooteco.prolog.member.domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

class DepartmentTypeTest {

    @ParameterizedTest
    @EnumSource(value = Part.class)
    void isContainedBy_null은_그룹명을_포함하지_않는다(Part DepartmentType) {
        assertThat(DepartmentType.isContainedBy(null)).isFalse();
    }

    @Test
    void isContainedBy() {
        assertThat(Part.ANDROID.isContainedBy("안드로이드 5기")).isTrue();
        assertThat(Part.ANDROID.isContainedBy("프론트엔드 5기")).isFalse();
        assertThat(Part.ANDROID.isContainedBy("백엔드 5기")).isFalse();

        assertThat(Part.FRONTEND.isContainedBy("안드로이드 5기")).isFalse();
        assertThat(Part.FRONTEND.isContainedBy("프론트엔드 5기")).isTrue();
        assertThat(Part.FRONTEND.isContainedBy("백엔드 5기")).isFalse();

        assertThat(Part.BACKEND.isContainedBy("안드로이드 5기")).isFalse();
        assertThat(Part.BACKEND.isContainedBy("프론트엔드 5기")).isFalse();
        assertThat(Part.BACKEND.isContainedBy("백엔드 5기")).isTrue();
    }
}
