package wooteco.prolog.roadmap.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import wooteco.prolog.roadmap.exception.CurriculumInvalidException;

class CurriculumTest {

    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    void 커리큘럼_생성시_이름에_공백이_들어올_경우_예외가_발생한다(String value) {
        Assertions.assertThatThrownBy(() -> new Curriculum(value))
            .isInstanceOf(CurriculumInvalidException.class);
    }

    @Test
    void 커리큘림_생성시_이름에_null_이_들어올경우_예외가_발생한다() {
        Assertions.assertThatThrownBy(() -> new Curriculum(null))
            .isInstanceOf(CurriculumInvalidException.class);
    }


    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    void 커리큘럼_수정시_이름에_공백이_들어올_경우_예외가_발생한다(String value) {
        final Curriculum curriculum = new Curriculum("기본값");
        Assertions.assertThatThrownBy(() -> curriculum.updateName(value))
            .isInstanceOf(CurriculumInvalidException.class);
    }

    @Test
    void 커리큘림_수정시_이름에_null_이_들어올경우_예외가_발생한다() {
        final Curriculum curriculum = new Curriculum("기본값");
        Assertions.assertThatThrownBy(() -> curriculum.updateName(null))
            .isInstanceOf(CurriculumInvalidException.class);
    }
}
