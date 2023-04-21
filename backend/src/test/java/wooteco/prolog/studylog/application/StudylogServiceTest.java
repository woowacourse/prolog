package wooteco.prolog.studylog.application;

import org.junit.jupiter.api.DisplayName;
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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
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

    @Test
    @DisplayName("스터디로그를 정상적으로 생성하고 반환한다.")
    void insertStudyLog() {
        // given
        String title = "제목";
        String content = "내용";
        Tags tags = Tags.of(Arrays.asList("스터디로그"));
        Member member = new Member(1L, "김동해", "오션", Role.CREW, 1L, "image");
        List<TagRequest> tagRequests = Collections.singletonList(new TagRequest("스터디로그"));
        List<TagResponse> tagResponses = Collections.singletonList(new TagResponse(null, "스터디로그"));
        StudylogRequest studylogRequest = new StudylogRequest(title, content, null, null, tagRequests);
        Studylog studylog = new Studylog(member, studylogRequest.getTitle(), studylogRequest.getContent(), null, null, tags.getList());
        StudylogTemp studylogTemp = new StudylogTemp(member, studylogRequest.getTitle(), studylogRequest.getContent(), null, null, tags.getList());

        when(memberService.findById(anyLong())).thenReturn(member);
        when(tagService.findOrCreate(anyList())).thenReturn(tags);
        when(studylogRepository.save(any())).thenReturn(studylog);

        // when, then
        assertThat(studylogService.findStudylogTemp(1L)).isNotNull();

        when(studylogTempRepository.existsByMemberId(1L)).thenReturn(true);

        StudylogResponse studylogResponse = studylogService.insertStudylog(1L, studylogRequest);

        assertThat(studylogResponse.getTitle()).isEqualTo(title);
        assertThat(studylogResponse.getContent()).isEqualTo(content);
        assertThat(studylogResponse.getTags()).usingRecursiveComparison().isEqualTo(tagResponses);
        verify(studylogTempRepository, times(1)).deleteByMemberId(1L);
    }

    @Test
    @DisplayName("임시 스터디로그를 정상적으로 생성하고 반환한다.")
    void insertStudylogTemp() {
        // given
        String title = "제목";
        String content = "내용";
        Tags tags = Tags.of(Arrays.asList("스터디로그"));
        Member member = new Member(1L, "문채원", "라온", Role.CREW, 1L, "image");
        List<TagRequest> tagRequests = Arrays.asList(new TagRequest("스터디로그"));
        List<TagResponse> tagResponses = Arrays.asList(new TagResponse(null, "스터디로그"));
        StudylogRequest studylogRequest = new StudylogRequest(title, content, null, null, tagRequests);
        StudylogTemp studylogTemp = new StudylogTemp(member, studylogRequest.getTitle(), studylogRequest.getContent(), null, null, tags.getList());

        when(memberService.findById(anyLong())).thenReturn(member);
        when(tagService.findOrCreate(anyList())).thenReturn(tags);
        when(studylogTempRepository.save(any())).thenReturn(studylogTemp);

        // when
        StudylogTempResponse studylogTempResponse = studylogService.insertStudylogTemp(1L, studylogRequest);

        // then
        assertThat(studylogTempResponse.getTitle()).isEqualTo(title);
        assertThat(studylogTempResponse.getContent()).isEqualTo(content);
        assertThat(studylogTempResponse.getTags()).usingRecursiveComparison().isEqualTo(tagResponses);
    }
}
