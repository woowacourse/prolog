package wooteco.prolog.member.domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static wooteco.prolog.member.domain.Part.*;

import org.junit.jupiter.api.Test;
import wooteco.prolog.common.exception.BadRequestException;

class DepartmentTest {

    private static final Department ANDROID_GROUP = new Department(null, "안드로이드", "5기");
    private static final Department BACKEND_GROUP = new Department(null, "백엔드", "5기");
    private static final Department FRONTEND_GROUP = new Department(null, "프론트엔드", "4기");

    @Test
    void getGroupType_이름이_그룹명을_포함하면_그룹을_반환한다() {
        assertThat(ANDROID_GROUP.getPart()).isEqualTo(ANDROID);
        assertThat(BACKEND_GROUP.getPart()).isEqualTo(BACKEND);
        assertThat(FRONTEND_GROUP.getPart()).isEqualTo(FRONTEND);
    }

    @Test
    void getGroupType_이름이_포함하는_그룹명이_없으면_예외가_발생한다() {
        Department department = new Department(null, "테스트", "test");

        assertThatThrownBy(department::getPart)
            .isInstanceOf(BadRequestException.class)
            .hasMessage("해당 그룹의 타입을 결정할 수 없습니다.");
    }

}
