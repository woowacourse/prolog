package wooteco.prolog.roadmap.application;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.TestConstructor;
import wooteco.prolog.roadmap.application.dto.CurriculumRequest;
import wooteco.prolog.roadmap.exception.CurriculumInvalidException;
import wooteco.support.utils.IntegrationTest;

@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@IntegrationTest
class CurriculumServiceTest {

    private final CurriculumService curriculumService;

    CurriculumServiceTest(CurriculumService curriculumService) {
        this.curriculumService = curriculumService;
    }

    @Test
    void 커리큘림_생성시_이름에_null_이_들어올경우_예외가_발생한다() {
        // given
        final CurriculumRequest request = new CurriculumRequest(null);

        // when&then
        Assertions.assertThatThrownBy(() -> curriculumService.create(request))
            .isInstanceOf(CurriculumInvalidException.class);
    }

    @Test
    void 커리큘림_생성시_이름에_빈공백이_들어올경우_예외가_발생한다() {
        // given
        final CurriculumRequest request = new CurriculumRequest(" ");

        // when&then
        Assertions.assertThatThrownBy(() -> curriculumService.create(request))
            .isInstanceOf(CurriculumInvalidException.class);
    }

    @Test
    void 커리큘림_생성시_이름에_공백이_들어올경우_예외가_발생한다() {
        // given
        final CurriculumRequest request = new CurriculumRequest("");

        // when&then
        Assertions.assertThatThrownBy(() -> curriculumService.create(request))
            .isInstanceOf(CurriculumInvalidException.class);
    }

}
