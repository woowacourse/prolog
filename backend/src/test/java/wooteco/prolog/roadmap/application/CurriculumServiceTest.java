package wooteco.prolog.roadmap.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import wooteco.prolog.roadmap.application.dto.CurriculumRequest;
import wooteco.prolog.roadmap.application.dto.CurriculumResponses;
import wooteco.prolog.roadmap.domain.Curriculum;
import wooteco.prolog.roadmap.domain.repository.CurriculumRepository;
import wooteco.prolog.roadmap.exception.CurriculumNotFoundException;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CurriculumServiceTest {

    @InjectMocks
    private CurriculumService curriculumService;

    @Mock
    private CurriculumRepository curriculumRepository;

    @DisplayName("커리큘럼 요청 정보에서 이름을 기반으로 새로운 커리큘럼을 만든다.")
    @Test
    void create() {
        //given
        final CurriculumRequest curriculumRequest = new CurriculumRequest("new Curriculum");
        given(curriculumRepository.save(any()))
            .willReturn(new Curriculum(1L, "new Curriculum"));

        //when
        final Long newCurriculumId = curriculumService.create(curriculumRequest);

        //then
        verify(curriculumRepository, times(1)).save(any());
        assertThat(newCurriculumId).isEqualTo(1);
    }

    @DisplayName("모든 curriculum 에 대한 응답을 보낸다.")
    @Test
    void findAll() {
        //given
        given(curriculumRepository.findAll())
            .willReturn(Arrays.asList(
                new Curriculum(1L, "curriculumA"),
                new Curriculum(1L, "curriculumA")));

        //when
        final CurriculumResponses curriculums = curriculumService.findCurriculums();

        //then
        assertThat(curriculums.getData()).hasSize(2);
    }

    @DisplayName("요청한 Id 를 가진 커리큘럼이 없으면 예외가 발생한다.")
    @Test
    void update_invalid_curriculumNotFound() {
        //given
        given(curriculumRepository.findById(anyLong()))
            .willReturn(Optional.empty());

        final CurriculumRequest noImpactRequest = null;

        //when, then
        assertThatThrownBy(() -> curriculumService.update(1L, noImpactRequest))
            .isInstanceOf(CurriculumNotFoundException.class);
    }

    @DisplayName("요청한 Id 를 가진 커리큘럼이 있으면 새로운 이름으로 업데이트 한다.")
    @Test
    void update() {
        //given
        final Curriculum curriculum = new Curriculum(1L, "Curriculum");

        given(curriculumRepository.findById(anyLong()))
            .willReturn(Optional.of(curriculum));

        final CurriculumRequest updateNameRequest = new CurriculumRequest("updated Name");

        //when
        curriculumService.update(1L, updateNameRequest);

        // then
        assertThat(curriculum.getName()).isEqualTo("updated Name");
    }

    @DisplayName("요청한 Id 를 가진 커리큘럼이 없으면 예외가 발생한다.")
    @Test
    void delete_invalid_curriculumNotFound() {
        //given
        given(curriculumRepository.findById(anyLong()))
            .willReturn(Optional.empty());

        //when, then
        assertThatThrownBy(() -> curriculumService.delete(1L))
            .isInstanceOf(CurriculumNotFoundException.class);
    }

    @DisplayName("요청한 Id 를 가진 커리큘럼이 있으면 삭제한다.")
    @Test
    void delete() {
        //given
        final Curriculum curriculum = new Curriculum(1L, "Curriculum");

        given(curriculumRepository.findById(anyLong()))
            .willReturn(Optional.of(curriculum));

        //when
        curriculumService.delete(1L);

        // then
        verify(curriculumRepository, times(1)).delete(curriculum);
    }
}
