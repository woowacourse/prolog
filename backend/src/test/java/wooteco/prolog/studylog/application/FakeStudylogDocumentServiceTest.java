package wooteco.prolog.studylog.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.anyIterable;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static wooteco.prolog.common.exception.BadRequestCode.STUDYLOG_DOCUMENT_NOT_FOUND;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import wooteco.prolog.common.exception.BadRequestException;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.member.domain.Role;
import wooteco.prolog.studylog.domain.Studylog;
import wooteco.prolog.studylog.domain.StudylogDocument;
import wooteco.prolog.studylog.domain.Tags;
import wooteco.prolog.studylog.domain.repository.StudylogDocumentRepository;
import wooteco.prolog.studylog.domain.repository.StudylogRepository;

@ExtendWith(MockitoExtension.class)
class FakeStudylogDocumentServiceTest {

    @Mock
    private StudylogDocumentRepository studylogDocumentRepository;
    @Mock
    private StudylogRepository studylogRepository;
    @InjectMocks
    private FakeStudylogDocumentService fakeStudylogDocumentService;


    @DisplayName("StudylogDocument save 호출 테스트")
    @Test
    void save() {
        //given
        final StudylogDocument studylogDocument = new StudylogDocument(1L, "라온", "오션", Arrays.asList(1L), 1L, 1L, "오션라온", LocalDateTime.now());
        when(studylogDocumentRepository.save(any())).thenReturn(any());

        //when
        fakeStudylogDocumentService.save(studylogDocument);

        //then
        verify(studylogDocumentRepository, times(1)).save(studylogDocument);
    }

    @DisplayName("StudylogDocument id로 조회 찾지 못할 경우 예외 테스트")
    @Test
    void findById_Exception() {
        //given
        when(studylogDocumentRepository.findById(1L)).thenReturn(Optional.empty());

        //when & then
        assertThatThrownBy(() -> fakeStudylogDocumentService.findById(1L))
            .isInstanceOf(BadRequestException.class)
            .hasMessage(STUDYLOG_DOCUMENT_NOT_FOUND.getMessage());

    }

    @DisplayName("StudylogDocument id로 조회 성공 테스트")
    @Test
    void findById() {
        //given
        final StudylogDocument studylogDocument = new StudylogDocument(1L, "라온", "오션", Arrays.asList(1L), 1L, 1L, "오션라온", LocalDateTime.now());
        when(studylogDocumentRepository.findById(1L)).thenReturn(Optional.of(studylogDocument));

        //when & then
        assertThat(fakeStudylogDocumentService.findById(1L)).isEqualTo(studylogDocument);
    }

    @DisplayName("StudylogDocument delete 호출 성공 테스트")
    @Test
    void delete() {
        //given
        final StudylogDocument studylogDocument = new StudylogDocument(1L, "라온", "오션", Arrays.asList(1L), 1L, 1L, "오션라온", LocalDateTime.now());
        doNothing().when(studylogDocumentRepository).delete(any());

        //when
        fakeStudylogDocumentService.delete(studylogDocument);

        //then
        verify(studylogDocumentRepository, times(1)).delete(studylogDocument);
    }

    @DisplayName("StudylogDocument update 호출 성공 테스트")
    @Test
    void update() {
        //given
        final StudylogDocument studylogDocument = new StudylogDocument(1L, "라온", "오션", Arrays.asList(1L), 1L, 1L, "오션라온", LocalDateTime.now());
        when(studylogDocumentRepository.save(any())).thenReturn(any());

        //when
        fakeStudylogDocumentService.update(studylogDocument);

        //then
        verify(studylogDocumentRepository, times(1)).save(studylogDocument);
    }

    @DisplayName("elasticsearch와 db간 동기화를 한다.")
    @Test
    void sync() {
        //given
        doNothing().when(studylogDocumentRepository).deleteAll();

        //when
        fakeStudylogDocumentService.sync();

        //then
        verify(studylogDocumentRepository, times(1)).deleteAll();
        verify(studylogRepository, times(1)).findAll();
        verify(studylogDocumentRepository, times(1)).saveAll(anyIterable());
    }

    @DisplayName("searchKeyword를 공백으로 전처리한다.")
    @Test
    void preprocess() {
        final String searchKeyword = "오션 라온";

        assertThat(fakeStudylogDocumentService.preprocess(searchKeyword))
            .containsExactly("오션", "라온", "오션 라온");
    }

    @DisplayName("searchkeyword로 StudylogDocument를 찾아 반환한다.")
    @Test
    void findBySearchKeyword() {
        //given
        final Tags tags = Tags.of(Collections.singletonList("스터디로그"));
        final Member member = new Member(1L, "김동해", "오션", Role.CREW, 1L, "image");
        final Studylog studylog = new Studylog(member, "제목", "내용", null, null, tags.getList());
        when(studylogRepository.findAll((Specification<Studylog>) any(), (Pageable) any())).thenReturn(new PageImpl<>(Arrays.asList(studylog)));

        //when
        fakeStudylogDocumentService.findBySearchKeyword("오션 라온", Arrays.asList(1L), Arrays.asList(1L), Arrays.asList(1L), Arrays.asList("오션", "라온"), LocalDate.now(), LocalDate.now(), Pageable.unpaged());

        // then
        verify(studylogRepository, times(1)).findAll((Specification<Studylog>) any(), (Pageable) any());
    }
}
