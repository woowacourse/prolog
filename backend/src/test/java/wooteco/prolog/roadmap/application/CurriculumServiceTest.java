package wooteco.prolog.roadmap.application;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.TestConstructor;
import wooteco.prolog.roadmap.application.dto.CurriculumRequest;
import wooteco.prolog.roadmap.application.dto.CurriculumResponses;
import wooteco.prolog.roadmap.domain.Curriculum;
import wooteco.prolog.roadmap.domain.repository.CurriculumRepository;
import wooteco.prolog.roadmap.exception.CurriculumNotFoundException;
import wooteco.support.utils.IntegrationTest;

@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@IntegrationTest
class CurriculumServiceTest {

    private final CurriculumService curriculumService;
    private final CurriculumRepository curriculumRepository;
    private final EntityManager em;

    CurriculumServiceTest(CurriculumService curriculumService,
                          CurriculumRepository curriculumRepository,
                          EntityManager em) {
        this.curriculumService = curriculumService;
        this.curriculumRepository = curriculumRepository;
        this.em = em;
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


    @Test
    void 커리쿨럼이_수정된다() {
        final Curriculum 커리큘럼1 = curriculumRepository.save(new Curriculum("커리큘럼1"));
        final String 수정된_이름 = "수정된 커리큘럼";
        final CurriculumRequest request = new CurriculumRequest(수정된_이름);

        curriculumService.update(커리큘럼1.getId(), request);
        curriculumRepository.flush();
        em.clear();

        final Curriculum 수정된_커리큘럼 = curriculumRepository.findById(커리큘럼1.getId()).get();

        Assertions.assertThat(수정된_커리큘럼.getName()).isEqualTo(수정된_이름);
    }


    @Test
    void 커리큘림_삭제시_없는_커리큘럼일_경우_예외가_발생한다() {
        // given
        final Long 없는_ID = 1000L;

        // when&then
        Assertions.assertThatThrownBy(() -> curriculumService.delete(없는_ID))
            .isInstanceOf(CurriculumNotFoundException.class);
    }

    @Test
    void 커리큘림이_삭제된다() {
        // given
        final Curriculum 커리큘럼1 = curriculumRepository.save(new Curriculum("커리큘럼1"));

        curriculumService.delete(커리큘럼1.getId());

        // when&then
        Assertions.assertThatThrownBy(() -> curriculumRepository.findById(커리큘럼1.getId()).get())
            .isInstanceOf(NoSuchElementException.class);
    }
}