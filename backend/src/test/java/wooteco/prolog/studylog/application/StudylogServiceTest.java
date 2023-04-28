package wooteco.prolog.studylog.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;
import wooteco.prolog.member.application.MemberService;
import wooteco.prolog.member.application.MemberTagService;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.member.domain.Role;
import wooteco.prolog.session.application.MissionService;
import wooteco.prolog.session.application.SessionService;
import wooteco.prolog.studylog.application.dto.*;
import wooteco.prolog.studylog.domain.Studylog;
import wooteco.prolog.studylog.domain.StudylogTemp;
import wooteco.prolog.studylog.domain.Tags;
import wooteco.prolog.studylog.domain.repository.*;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudylogServiceTest {

    @InjectMocks
    private StudylogService studylogService;
    @Mock
    private MemberTagService memberTagService;
    @Mock
    private DocumentService studylogDocumentService;
    @Mock
    private MemberService memberService;
    @Mock
    private TagService tagService;
    @Mock
    private SessionService sessionService;
    @Mock
    private MissionService missionService;
    @Mock
    private StudylogRepository studylogRepository;
    @Mock
    private StudylogScrapRepository studylogScrapRepository;
    @Mock
    private StudylogReadRepository studylogReadRepository;
    @Mock
    private StudylogTempRepository studylogTempRepository;
    @Mock
    private CommentRepository commentRepository;
    @Mock
    private ApplicationEventPublisher eventPublisher;

    @DisplayName("스터디로그를 정상적으로 생성하고 반환한다.")
    @Nested
    class insertStudylog {

        String title = "제목";
        String content = "내용";
        Tags tags = Tags.of(Collections.singletonList("스터디로그"));
        Member member = new Member(1L, "김동해", "오션", Role.CREW, 1L, "image");
        List<TagRequest> tagRequests = Collections.singletonList(new TagRequest("스터디로그"));
        StudylogRequest studylogRequest = new StudylogRequest(title, content, null, null, tagRequests);
        Studylog studylog = new Studylog(member, studylogRequest.getTitle(), studylogRequest.getContent(), null, null, tags.getList());
        List<TagResponse> expectedTagResponses = Collections.singletonList(new TagResponse(null, "스터디로그"));

        @Test
        @DisplayName("StudyLogTemp가 존재할 경우 삭제하고, 스터디로그를 정상적으로 생성하고 반환한다.")
        void insertStudylog_existStudyLogTemp() {
            // given
            when(memberService.findById(anyLong())).thenReturn(member);
            when(tagService.findOrCreate(anyList())).thenReturn(tags);
            when(studylogRepository.save(any())).thenReturn(studylog);
            when(studylogTempRepository.existsByMemberId(1L)).thenReturn(true);

            // when
            StudylogResponse studylogResponse = studylogService.insertStudylog(1L, studylogRequest);

            // then
            assertAll(() -> {
                assertThat(studylogResponse.getTitle()).isEqualTo(title);
                assertThat(studylogResponse.getContent()).isEqualTo(content);
                assertThat(studylogResponse.getTags()).usingRecursiveComparison().isEqualTo(expectedTagResponses);
                verify(studylogTempRepository, times(1)).deleteByMemberId(1L);
            });
        }

        @Test
        @DisplayName("StudyLogTemp가 존재하지 않는 경우, 스터디로그를 정상적으로 생성하고 반환한다.")
        void insertStudylog_notExistStudyLogTemp() {
            // given
            when(memberService.findById(anyLong())).thenReturn(member);
            when(tagService.findOrCreate(anyList())).thenReturn(tags);
            when(studylogRepository.save(any())).thenReturn(studylog);
            when(studylogTempRepository.existsByMemberId(1L)).thenReturn(false);

            // when
            StudylogResponse studylogResponse = studylogService.insertStudylog(1L, studylogRequest);

            // then
            assertAll(() -> {
                assertThat(studylogResponse.getTitle()).isEqualTo(title);
                assertThat(studylogResponse.getContent()).isEqualTo(content);
                assertThat(studylogResponse.getTags()).usingRecursiveComparison().isEqualTo(expectedTagResponses);
                verify(studylogTempRepository, never()).deleteByMemberId(1L);
            });
        }
    }

    @DisplayName("임시 스터디로그를 정상적으로 생성하고 반환한다.")
    @Nested
    class insertStudylogTemp {

        String title = "제목";
        String content = "내용";
        Tags tags = Tags.of(Collections.singletonList("스터디로그"));
        Member member = new Member(1L, "문채원", "라온", Role.CREW, 1L, "image");
        List<TagRequest> tagRequests = Collections.singletonList(new TagRequest("스터디로그"));
        List<TagResponse> tagResponses = Collections.singletonList(new TagResponse(null, "스터디로그"));
        StudylogRequest studylogRequest = new StudylogRequest(title, content, null, null, tagRequests);
        StudylogTemp studylogTemp = new StudylogTemp(member, studylogRequest.getTitle(), studylogRequest.getContent(), null, null, tags.getList());

        @Test
        @DisplayName("StudyLogTemp가 존재하지 않는 경우 삭제하고, 임시 스터디로그를 정상적으로 생성하고 반환한다.")
        void insertStudylogTemp_existStudylogTemp() {
            // given
            when(memberService.findById(anyLong())).thenReturn(member);
            when(tagService.findOrCreate(anyList())).thenReturn(tags);
            when(studylogTempRepository.save(any())).thenReturn(studylogTemp);
            when(studylogTempRepository.existsByMemberId(1L)).thenReturn(true);

            // when
            StudylogTempResponse studylogTempResponse = studylogService.insertStudylogTemp(1L, studylogRequest);

            // then
            assertAll(() -> {
                assertThat(studylogTempResponse.getTitle()).isEqualTo(title);
                assertThat(studylogTempResponse.getContent()).isEqualTo(content);
                assertThat(studylogTempResponse.getTags()).usingRecursiveComparison().isEqualTo(tagResponses);
                verify(studylogTempRepository, times(1)).deleteByMemberId(1L);
            });
        }


        @Test
        @DisplayName("StudyLogTemp가 존재하지 않는 경우, 임시 스터디로그를 정상적으로 생성하고 반환한다.")
        void insertStudylogTemp_notExistStudylogTemp() {
            // given
            when(memberService.findById(anyLong())).thenReturn(member);
            when(tagService.findOrCreate(anyList())).thenReturn(tags);
            when(studylogTempRepository.save(any())).thenReturn(studylogTemp);
            when(studylogTempRepository.existsByMemberId(1L)).thenReturn(false);


            // when
            StudylogTempResponse studylogTempResponse = studylogService.insertStudylogTemp(1L, studylogRequest);

            // then
            assertAll(() -> {
                assertThat(studylogTempResponse.getTitle()).isEqualTo(title);
                assertThat(studylogTempResponse.getContent()).isEqualTo(content);
                assertThat(studylogTempResponse.getTags()).usingRecursiveComparison().isEqualTo(tagResponses);
                verify(studylogTempRepository, never()).deleteByMemberId(1L);
            });
        }

    }
}
