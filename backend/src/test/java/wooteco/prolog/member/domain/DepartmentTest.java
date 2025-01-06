package wooteco.prolog.member.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static wooteco.prolog.member.domain.Part.ANDROID;
import static wooteco.prolog.member.domain.Part.BACKEND;
import static wooteco.prolog.member.domain.Part.FRONTEND;
import static wooteco.prolog.member.domain.Term.FIFTH;
import static wooteco.prolog.member.domain.Term.FOURTH;

class DepartmentTest {

    private static final Department ANDROID_DEPARTMENT = new Department(null, ANDROID, FIFTH);
    private static final Department BACKEND_DEPARTMENT = new Department(null, BACKEND, FIFTH);
    private static final Department FRONTEND_DEPARTMENT = new Department(null, FRONTEND, FOURTH);

    @Test
    void getPartType_이름이_그룹명을_포함하면_그룹을_반환한다() {
        assertThat(ANDROID_DEPARTMENT.getPart()).isEqualTo(ANDROID);
        assertThat(BACKEND_DEPARTMENT.getPart()).isEqualTo(BACKEND);
        assertThat(FRONTEND_DEPARTMENT.getPart()).isEqualTo(FRONTEND);
    }

    @Test
    void getPartType_이름이_포함하는_그룹명이_없으면_예외가_발생한다() {

        assertThatThrownBy(() -> new Department(null, "테스트", "test"))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("name과 일치하는 part가 존재하지 않습니다.");
    }

}
