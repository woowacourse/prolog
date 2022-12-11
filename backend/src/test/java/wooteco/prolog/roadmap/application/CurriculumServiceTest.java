package wooteco.prolog.roadmap.application;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.TestConstructor;
import wooteco.prolog.roadmap.application.dto.CurriculumRequest;
import wooteco.prolog.roadmap.application.dto.CurriculumResponses;
import wooteco.prolog.roadmap.domain.Curriculum;
import wooteco.prolog.roadmap.domain.repository.CurriculumRepository;
import wooteco.prolog.roadmap.exception.CurriculumInvalidException;
import wooteco.support.utils.IntegrationTest;

@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@IntegrationTest
class CurriculumServiceTest {

    private final CurriculumService curriculumService;
    private final CurriculumRepository curriculumRepository;

    CurriculumServiceTest(CurriculumService curriculumService,
                          CurriculumRepository curriculumRepository) {
        this.curriculumService = curriculumService;
        this.curriculumRepository = curriculumRepository;
    }


    @Test
    void 커리큘럼이_생성된다() {
        // given
        final CurriculumRequest request = new CurriculumRequest("수달이 작성한 커리큘럼");

        // when&then
        final Long savedId = curriculumService.create(request);

        Assertions.assertThat(savedId).isNotNull();
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

    @Test
    void 커리쿨럼_목록이_조회된다() {
        final Curriculum 커리큘럼1 = curriculumRepository.save(new Curriculum("커리큘럼1"));
        final Curriculum 커리큘럼2 = curriculumRepository.save(new Curriculum("커리큘럼2"));

        final List<Curriculum> expected = Arrays.asList(커리큘럼1, 커리큘럼2);
        final CurriculumResponses actual = curriculumService.findCurriculums();

        Assertions.assertThat(actual.getData()).extracting("name")
            .containsAll(expected.stream()
                .map(Curriculum::getName)
                .collect(Collectors.toList()));
    }
}
