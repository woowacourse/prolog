package wooteco.prolog.studylog.application;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static wooteco.prolog.common.exception.BadRequestCode.ONLY_AUTHOR_CAN_EDIT;
import static wooteco.prolog.common.exception.BadRequestCode.STUDYLOG_ARGUMENT;
import static wooteco.prolog.common.exception.BadRequestCode.STUDYLOG_NOT_FOUND;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import wooteco.prolog.common.exception.BadRequestException;
import wooteco.prolog.login.ui.LoginMember;
import wooteco.prolog.member.application.MemberService;
import wooteco.prolog.member.application.MemberTagService;
import wooteco.prolog.member.application.dto.MemberResponse;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.member.domain.Role;
import wooteco.prolog.session.application.AnswerService;
import wooteco.prolog.session.application.MissionService;
import wooteco.prolog.session.application.SessionService;
import wooteco.prolog.session.application.dto.MissionResponse;
import wooteco.prolog.session.application.dto.SessionResponse;
import wooteco.prolog.session.domain.Mission;
import wooteco.prolog.session.domain.Session;
import wooteco.prolog.studylog.application.dto.StudylogDocumentResponse;
import wooteco.prolog.studylog.application.dto.StudylogMissionRequest;
import wooteco.prolog.studylog.application.dto.StudylogRequest;
import wooteco.prolog.studylog.application.dto.StudylogResponse;
import wooteco.prolog.studylog.application.dto.StudylogRssFeedResponse;
import wooteco.prolog.studylog.application.dto.StudylogSessionRequest;
import wooteco.prolog.studylog.application.dto.StudylogTempResponse;
import wooteco.prolog.studylog.application.dto.StudylogsResponse;
import wooteco.prolog.studylog.application.dto.TagRequest;
import wooteco.prolog.studylog.application.dto.TagResponse;
import wooteco.prolog.studylog.application.dto.search.StudylogsSearchRequest;
import wooteco.prolog.studylog.domain.Studylog;
import wooteco.prolog.studylog.domain.StudylogRead;
import wooteco.prolog.studylog.domain.StudylogScrap;
import wooteco.prolog.studylog.domain.StudylogTemp;
import wooteco.prolog.studylog.domain.Tag;
import wooteco.prolog.studylog.domain.Tags;
import wooteco.prolog.studylog.domain.repository.CommentRepository;
import wooteco.prolog.studylog.domain.repository.StudylogReadRepository;
import wooteco.prolog.studylog.domain.repository.StudylogRepository;
import wooteco.prolog.studylog.domain.repository.StudylogScrapRepository;
import wooteco.prolog.studylog.domain.repository.StudylogTempRepository;
import wooteco.prolog.studylog.domain.repository.dto.CommentCount;
import wooteco.prolog.studylog.event.StudylogDeleteEvent;

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
    private AnswerService answerService;
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
        Tags tags = Tags.of(singletonList("스터디로그"));
        Member member = new Member(1L, "김동해", "오션", Role.CREW, 1L, "image");
        List<TagRequest> tagRequests = singletonList(new TagRequest("스터디로그"));
        StudylogRequest studylogRequest = new StudylogRequest(title, content, null, null, tagRequests, null);
        Studylog studylog = new Studylog(member, studylogRequest.getTitle(),
            studylogRequest.getContent(), null, null, tags.getList());
        List<TagResponse> expectedTagResponses = singletonList(new TagResponse(null, "스터디로그"));

        @DisplayName("StudyLogTemp가 존재할 경우 삭제하고, 스터디로그를 정상적으로 생성하고 반환한다.")
        @Test
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
                assertThat(studylogResponse.getTags()).usingRecursiveComparison()
                    .isEqualTo(expectedTagResponses);
                verify(studylogTempRepository, times(1)).deleteByMemberId(1L);
            });
        }

        @DisplayName("StudyLogTemp가 존재하지 않는 경우, 스터디로그를 정상적으로 생성하고 반환한다.")
        @Test
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
                assertThat(studylogResponse.getTags()).usingRecursiveComparison()
                    .isEqualTo(expectedTagResponses);
                verify(studylogTempRepository, never()).deleteByMemberId(1L);
            });
        }
    }

    @DisplayName("임시 스터디로그를 정상적으로 생성하고 반환한다.")
    @Nested
    class insertStudylogTemp {

        String title = "제목";
        String content = "내용";
        Tags tags = Tags.of(singletonList("스터디로그"));
        Member member = new Member(1L, "문채원", "라온", Role.CREW, 1L, "image");
        List<TagRequest> tagRequests = singletonList(new TagRequest("스터디로그"));
        List<TagResponse> tagResponses = singletonList(new TagResponse(null, "스터디로그"));
        StudylogRequest studylogRequest = new StudylogRequest(title, content, null, null, tagRequests, null);
        StudylogTemp studylogTemp = new StudylogTemp(member, studylogRequest.getTitle(),
            studylogRequest.getContent(), null, null, tags.getList());

        @DisplayName("StudyLogTemp가 존재하지 않는 경우 삭제하고, 임시 스터디로그를 정상적으로 생성하고 반환한다.")
        @Test
        void insertStudylogTemp_existStudylogTemp() {
            // given
            when(memberService.findById(anyLong())).thenReturn(member);
            when(tagService.findOrCreate(anyList())).thenReturn(tags);
            when(studylogTempRepository.save(any())).thenReturn(studylogTemp);
            when(studylogTempRepository.existsByMemberId(1L)).thenReturn(true);
            when(answerService.saveAnswerTemp(any(), any(), any())).thenReturn(emptyList());

            // when
            StudylogTempResponse studylogTempResponse = studylogService.insertStudylogTemp(1L,
                studylogRequest);

            // then
            assertAll(() -> {
                assertThat(studylogTempResponse.getTitle()).isEqualTo(title);
                assertThat(studylogTempResponse.getContent()).isEqualTo(content);
                assertThat(studylogTempResponse.getTags()).usingRecursiveComparison()
                    .isEqualTo(tagResponses);
                verify(studylogTempRepository, times(1)).deleteByMemberId(1L);
            });
        }


        @DisplayName("StudyLogTemp가 존재하지 않는 경우, 임시 스터디로그를 정상적으로 생성하고 반환한다.")
        @Test
        void insertStudylogTemp_notExistStudylogTemp() {
            // given
            when(memberService.findById(anyLong())).thenReturn(member);
            when(tagService.findOrCreate(anyList())).thenReturn(tags);
            when(studylogTempRepository.save(any())).thenReturn(studylogTemp);
            when(studylogTempRepository.existsByMemberId(1L)).thenReturn(false);

            // when
            StudylogTempResponse studylogTempResponse = studylogService.insertStudylogTemp(1L,
                studylogRequest);

            // then
            assertAll(() -> {
                assertThat(studylogTempResponse.getTitle()).isEqualTo(title);
                assertThat(studylogTempResponse.getContent()).isEqualTo(content);
                assertThat(studylogTempResponse.getTags()).usingRecursiveComparison()
                    .isEqualTo(tagResponses);
                verify(studylogTempRepository, never()).deleteByMemberId(1L);
            });
        }


    }

    @DisplayName("스크랩한 게시물들의 게시물 id들을 가져올 수 있다")
    @Test
    void findScrapIds() {
        //given
        final Member member = new Member(1L, "hihi", "연어", Role.CREW, 1L, "image");
        final Mission mission = new Mission(1L, "지하철", new Session("BE 레벨2"));
        final Studylog studylog = new Studylog(member, "짜장면", "먹고싶다", mission,
            singletonList(new Tag("스프링")));
        final StudylogScrap scrap = new StudylogScrap(member, studylog);

        when(studylogScrapRepository.findByMemberId(anyLong())).thenReturn(singletonList(scrap));

        //when
        final List<Long> scrapIds = studylogService.findScrapIds(member.getId());

        //then
        assertAll(
            () -> assertThat(scrapIds).hasSize(1),
            () -> assertThat(scrapIds.get(0)).isEqualTo(studylog.getId()));
    }

    @DisplayName("키워드 외의 조건들로 스터디로그를 조회할 수 있다")
    @Test
    void findStudylogsWithoutKeyword() {
        //given
        when(studylogRepository.findAll((Specification<Studylog>) any(), (Pageable) any()))
            .thenReturn(Page.empty());
        when(answerService.findAnswersByStudylogs(any())).thenReturn(Collections.emptyMap());

        //when
        studylogService.findStudylogsWithoutKeyword(emptyList(),
            emptyList(),
            emptyList(),
            emptyList(),
            emptyList(),
            LocalDate.MIN,
            LocalDate.MAX,
            Pageable.unpaged(),
            1L);

        //then
        verify(studylogRepository, times(1)).findAll((Specification<Studylog>) any(),
            (Pageable) any());
    }

    @DisplayName("StudylogResponse의 scrap 여부를 설정할 수 있다")
    @Test
    void updateScrap() {
        //given
        final List<Long> scrapIds = Arrays.asList(1L, 2L);
        final StudylogResponse studylogResponse = new StudylogResponse(1L, new MemberResponse(),
            LocalDateTime.now(), LocalDateTime.now(), new SessionResponse(), new MissionResponse(),
            "파라미터란?", "제곧내", emptyList(), false, false, 0, false, 0);

        final boolean beforeUpdate = studylogResponse.isScrap();

        //when
        studylogService.updateScrap(singletonList(studylogResponse), scrapIds);

        //then
        assertAll(
            () -> assertThat(beforeUpdate).isFalse(),
            () -> assertThat(studylogResponse.isScrap()).isTrue());
    }

    @DisplayName("insertStudyLogs를 호출할 때 빈 리스트로 요청하면 예외가 발생한다")
    @Test
    void insertStudyLogs_fail() {
        //when, then
        assertThatThrownBy(() -> studylogService.insertStudylogs(1L, emptyList()))
            .isInstanceOf(BadRequestException.class)
            .hasMessage(STUDYLOG_ARGUMENT.getMessage());
    }

    @DisplayName("스터디로그 여러 개를 저장할 수 있다")
    @Test
    void insertStudyLogs() {
        //given
        final StudylogRequest request = new StudylogRequest("지하철미션", "어렵당", 1L, emptyList());
        final List<StudylogRequest> studylogRequests = Arrays.asList(request, request, request);

        final Member member = new Member(1L, "hihi", "연어", Role.CREW, 1L, "image");
        final Mission mission = new Mission(1L, "지하철", new Session("BE 레벨2"));
        when(studylogRepository.save(any())).thenReturn(
            new Studylog(member, "제목", "내용", mission, emptyList()));
        when(memberService.findById(any())).thenReturn(member);
        when(tagService.findOrCreate(any())).thenReturn(new Tags(emptyList()));
        when(answerService.saveAnswers(anyLong(), any(), any())).thenReturn(emptyList());

        //when
        studylogService.insertStudylogs(1L, studylogRequests);

        //then
        verify(studylogRepository, times(studylogRequests.size())).save(any());
    }


    @DisplayName("해당 Id를 가진 studylog 가 없으면 예외를 발생시킨다.")
    @Test
    void findStudylogById() {
        //given
        given(studylogRepository.findById(anyLong()))
            .willReturn(Optional.empty());

        //when, then
        assertThatThrownBy(() -> studylogService.findStudylogById(1L))
            .isInstanceOf(BadRequestException.class)
            .hasMessage(STUDYLOG_NOT_FOUND.getMessage());
    }

    @Nested
    class retrieveStudylogById {

        private Studylog studylog;
        private long studyLogId = 1L;

        @BeforeEach
        void setting() throws NoSuchFieldException, IllegalAccessException {
            studylog = new Studylog(
                new Member(1L, "최현구", "현구막", Role.CREW, 1L, "image"),
                "제목",
                "내용",
                new Mission("자동차 미션", new Session("세션 1")),
                Arrays.asList(new Tag("Java"), new Tag("Spring"))
            );

            //Jpa 를 대신하여 reflection 을 통해 아이디 할당
            final Field id = studylog.getClass().getDeclaredField("id");
            id.setAccessible(true);
            id.set(studylog, studyLogId);
        }

        @DisplayName("익명 유저가 id 기반으로 스터디 로그를 검색할 경우 읽지 않은 글이라면 조회 수를 증가시키고 글을 반환한다")
        @Test
        void retrieveStudylogById_anonymous() {
            //given
            final LoginMember loginMember = new LoginMember(LoginMember.Authority.ANONYMOUS);
            given(studylogRepository.findById(anyLong()))
                .willReturn(Optional.of(studylog));
            given(answerService.findAnswersByStudylogId(anyLong()))
                .willReturn(emptyList());

            final int previousViewCount = studylog.getViewCount();

            //when
            final StudylogResponse studylogResponse = studylogService.retrieveStudylogById(
                loginMember, 1L, false);

            //then
            assertAll(
                () -> assertThat(studylogResponse).extracting(StudylogResponse::getViewCount)
                    .isEqualTo(previousViewCount + 1),
                () -> assertThat(studylogResponse).extracting(StudylogResponse::getId)
                    .isEqualTo(studyLogId)
            );
        }

        @DisplayName("다른 유저의 글을 읽을 경우 과거 조회 여무에 다라 조회수를 증가 혹은 유지하고, 해당 글을 스크랩 혹은 좋아요를 눌렸는지와 함께 반환한다.")
        @Test
        void retrieveStudylogById_readOtherUserStudylog() {
            //given
            final long otherUserId = 2L;
            final LoginMember loginMember = new LoginMember(otherUserId,
                LoginMember.Authority.MEMBER);
            given(studylogRepository.findById(anyLong()))
                .willReturn(Optional.of(studylog));
            given(studylogReadRepository.findByMemberIdAndStudylogId(anyLong(), anyLong()))
                .willReturn(Optional.of(new StudylogRead(null, null)));
            given(studylogScrapRepository.findByMemberIdAndStudylogId(anyLong(), anyLong()))
                .willReturn(Optional.of(new StudylogScrap(null, null)));
            given(memberService.findById(anyLong()))
                .willReturn(new Member(otherUserId, null, null, null, null, null));
            given(answerService.findAnswersByStudylogId(anyLong())).willReturn(emptyList());

            final int previousViewCount = studylog.getViewCount();

            //when
            final StudylogResponse studylogResponse = studylogService.retrieveStudylogById(
                loginMember, 1L, false);

            //then
            assertAll(
                () -> assertThat(studylogResponse).extracting(StudylogResponse::getViewCount)
                    .isEqualTo(previousViewCount + 1),
                () -> assertThat(studylogResponse).extracting(StudylogResponse::getId)
                    .isEqualTo(studyLogId),
                () -> assertThat(studylogResponse).extracting(StudylogResponse::isRead)
                    .isEqualTo(true),
                () -> assertThat(studylogResponse).extracting(StudylogResponse::isScrap)
                    .isEqualTo(true)
            );
        }

        @DisplayName("자신 유저의 글을 읽을 조회 여부에 상관없이 조회 수를 유지하고, 해당 글을 스크랩 혹은 좋아요를 눌렸는지와 함께 반환한다.")
        @Test
        void retrieveStudylogById_readMineStudyLog() {
            //given
            final LoginMember loginMember = new LoginMember(1L, LoginMember.Authority.MEMBER);
            given(studylogRepository.findById(anyLong()))
                .willReturn(Optional.of(studylog));
            given(studylogReadRepository.findByMemberIdAndStudylogId(anyLong(), anyLong()))
                .willReturn(Optional.of(new StudylogRead(null, null)));
            given(studylogScrapRepository.findByMemberIdAndStudylogId(anyLong(), anyLong()))
                .willReturn(Optional.of(new StudylogScrap(null, null)));
            given(memberService.findById(anyLong()))
                .willReturn(new Member(1L, null, null, null, null, null));
            given(answerService.findAnswersByStudylogId(anyLong())).willReturn(emptyList());

            final int previousViewCount = studylog.getViewCount();

            //when
            final StudylogResponse studylogResponse = studylogService.retrieveStudylogById(
                loginMember, 1L, false);

            //then
            assertAll(
                () -> assertThat(studylogResponse).extracting(StudylogResponse::getViewCount)
                    .isEqualTo(previousViewCount),
                () -> assertThat(studylogResponse).extracting(StudylogResponse::getId)
                    .isEqualTo(studyLogId),
                () -> assertThat(studylogResponse).extracting(StudylogResponse::isRead)
                    .isEqualTo(true),
                () -> assertThat(studylogResponse).extracting(StudylogResponse::isScrap)
                    .isEqualTo(true)
            );
        }
    }

    @Nested
    class deleteStudylog {

        private Studylog studylog;
        private long studyLogId = 1L;

        @BeforeEach
        void setting() throws NoSuchFieldException, IllegalAccessException {
            studylog = new Studylog(
                new Member(1L, "최현구", "현구막", Role.CREW, 1L, "image"),
                "제목",
                "내용",
                new Mission("자동차 미션", new Session("세션 1")),
                Arrays.asList(new Tag("Java"), new Tag("Spring"))
            );

            //Jpa 를 대신하여 reflection 을 통해 아이디 할당
            final Field id = studylog.getClass().getDeclaredField("id");
            id.setAccessible(true);
            id.set(studylog, studyLogId);
        }

        @DisplayName("스터디 로그가 없으면 예외가 발생한다")
        @Test
        void deleteStudylog_exception_notExistingStudylog() {
            //given
            given(memberService.findById(anyLong()))
                .willReturn(new Member(1L, null, null, null, null, null));
            given(studylogRepository.findById(anyLong()))
                .willReturn(Optional.empty());

            //when, then
            assertThatThrownBy(() -> studylogService.deleteStudylog(1L, 1L))
                .isInstanceOf(BadRequestException.class)
                .hasMessage(STUDYLOG_NOT_FOUND.getMessage());
        }

        @DisplayName("자신이 것이 아닌 스터디 로그를 삭제하면 예외가 발생한다")
        @Test
        void deleteStudylog_exception_notMine() {
            //given
            given(memberService.findById(anyLong()))
                .willReturn(new Member(2L, null, null, null, null, null));
            given(studylogRepository.findById(anyLong()))
                .willReturn(Optional.of(studylog));

            //when, then
            assertThatThrownBy(() -> studylogService.deleteStudylog(2L, 1L))
                .isInstanceOf(BadRequestException.class)
                .hasMessage(ONLY_AUTHOR_CAN_EDIT.getMessage());
        }

        @DisplayName("해당 StudyLog 에 대한 스크랩과 Read 와 함께 StudyLog 를 삭제한다.")
        @Test
        void deleteStudylog() {
            //given
            given(memberService.findById(anyLong()))
                .willReturn(new Member(1L, null, null, null, null, null));
            given(studylogRepository.findById(anyLong()))
                .willReturn(Optional.of(studylog));

            given(studylogScrapRepository.existsByMemberIdAndStudylogId(anyLong(), anyLong()))
                .willReturn(true);
            given(studylogScrapRepository.findByMemberIdAndStudylogId(anyLong(), anyLong()))
                .willReturn(Optional.of(new StudylogScrap(null, null)));

            given(studylogReadRepository.existsByMemberIdAndStudylogId(anyLong(), anyLong()))
                .willReturn(false);

            //when
            studylogService.deleteStudylog(1L, 1L);

            //then
            verify(tagService, times(1)).findByStudylogsAndMember(any(), any());
            verify(studylogDocumentService, times(1)).delete(any());
            verify(memberTagService, times(1)).removeMemberTag(any(), any());
            verify(eventPublisher, times(1)).publishEvent(any(StudylogDeleteEvent.class));

            verify(studylogScrapRepository, times(1)).delete(any());
            verify(studylogReadRepository, times(0)).delete(any());
        }
    }

    @Nested
    class updateStudylogMIssion {

        private Studylog studylog;
        private long studyLogId = 1L;

        @BeforeEach
        void setting() throws NoSuchFieldException, IllegalAccessException {
            studylog = new Studylog(
                new Member(1L, "최현구", "현구막", Role.CREW, 1L, "image"),
                "제목",
                "내용",
                new Mission("자동차 미션", new Session("세션 1")),
                Arrays.asList(new Tag("Java"), new Tag("Spring"))
            );

            //Jpa 를 대신하여 reflection 을 통해 아이디 할당
            final Field id = studylog.getClass().getDeclaredField("id");
            id.setAccessible(true);
            id.set(studylog, studyLogId);
        }

        @DisplayName("없는 스터디로그에 대한 Mission 업데이트 요청을 보내면 예외가 발생한다.")
        @Test
        void updateStudylogMission_exception_notExistingStudylog() {
            //given
            given(studylogRepository.findById(anyLong()))
                .willReturn(Optional.empty());

            //when, then
            assertThatThrownBy(() -> studylogService.updateStudylogMission(1L, 1L, null))
                .isInstanceOf(BadRequestException.class)
                .hasMessage(STUDYLOG_NOT_FOUND.getMessage());
        }

        @DisplayName("자신의 스터디로그가 아니라면 예외가 발생한다.")
        @Test
        void updateStudylogMission_exception_notMine() {
            //given
            given(studylogRepository.findById(anyLong()))
                .willReturn(Optional.of(studylog));

            //when, then
            assertThatThrownBy(() -> studylogService.updateStudylogMission(2L, 1L, null))
                .isInstanceOf(BadRequestException.class)
                .hasMessage(ONLY_AUTHOR_CAN_EDIT.getMessage());
        }

        @DisplayName("스터디로그의 미션을 업데이트한다.")
        @Test
        void updateStudylogMission() {
            //given
            final Mission newMission = new Mission("mission", null);

            given(studylogRepository.findById(anyLong()))
                .willReturn(Optional.of(studylog));
            given(missionService.findMissionById(anyLong()))
                .willReturn(Optional.of(newMission));

            //when
            studylogService.updateStudylogMission(1L, 1L, new StudylogMissionRequest(1L));

            //then
            assertThat(studylog).extracting(Studylog::getMission).isEqualTo(newMission);
        }
    }

    @Nested
    class findStudylogs {

        private Studylog studylog;

        @BeforeEach
        void setting() throws NoSuchFieldException, IllegalAccessException {
            studylog = new Studylog(
                new Member(1L, "최현구", "현구막", Role.CREW, 1L, "image"),
                "제목",
                "내용",
                new Mission("자동차 미션", new Session("세션 1")),
                Arrays.asList(new Tag("Java"), new Tag("Spring"))
            );
        }

        @DisplayName("StudylogsSearchRequest 에 Ids 필드가 지정되었다면 해당 Id 들을 기반으로 검색 결과를 반환한다.")
        @Test
        void findStudylogs_responseByRequestIds() {
            //given
            final int requestPage = 0;
            final int pageSize = 10;
            final Pageable pageableRequest = PageRequest.of(requestPage, pageSize);
            final StudylogsSearchRequest studylogsSearchRequest = new StudylogsSearchRequest(
                null,
                null,
                null,
                null,
                null, null,
                null,
                null,
                Arrays.asList(1L, 2L, 3L),
                pageableRequest
            );

            final int findElementCounts = 1;
            given(studylogRepository.findByIdInAndDeletedFalseOrderByIdDesc(any(), any()))
                .willReturn(new PageImpl<>(
                    Arrays.asList(studylog),
                    pageableRequest,
                    findElementCounts
                ));

            given(commentRepository.countByStudylogIn(anyList()))
                .willReturn(Arrays.asList(
                        new CommentCount(studylog, 1L)
                    )
                );

            //when
            final StudylogsResponse studylogsResponse = studylogService.findStudylogs(
                studylogsSearchRequest, 1L);
            int oneIndexedParameter = 1;

            //then
            assertAll(
                () -> assertThat(studylogsResponse.getCurrPage()).isEqualTo(
                    requestPage + oneIndexedParameter),
                () -> assertThat(studylogsResponse.getTotalSize()).isEqualTo(findElementCounts),
                () -> assertThat(studylogsResponse.getTotalPage()).isEqualTo(
                    (findElementCounts / pageSize) + 1),
                () -> assertThat(studylogsResponse.getData()).hasSize(1)
            );
        }

        @DisplayName("StudyLog 검색 요청에 1. id가 없고, 2. 키워드가 없다면 나머지 요청 정보들을 기반으로 StudyLog 를 특정한다.")
        @Test
        void findStudylogs_findBySpecs() {
            //given
            final int requestPage = 0;
            final int pageSize = 10;
            final Pageable pageableRequest = PageRequest.of(requestPage, pageSize);
            final StudylogsSearchRequest studylogsSearchRequest = new StudylogsSearchRequest(
                null,
                null,
                null,
                null,
                null, null,
                null,
                null,
                null,
                pageableRequest
            );

            final int findElementCounts = 1;
            given(studylogRepository.findAll(any(Specification.class), any(Pageable.class)))
                .willReturn(
                    new PageImpl<>(
                        Arrays.asList(studylog),
                        pageableRequest,
                        findElementCounts
                    )
                );

            given(commentRepository.countByStudylogIn(anyList()))
                .willReturn(Arrays.asList(
                        new CommentCount(studylog, 1L)
                    )
                );

            given(answerService.findAnswersByStudylogs(any()))
                .willReturn(Collections.emptyMap());

            //when
            final StudylogsResponse studylogsResponse = studylogService.findStudylogs(
                studylogsSearchRequest, 1L);
            int oneIndexedParameter = 1;

            //then
            assertAll(
                () -> assertThat(studylogsResponse.getCurrPage()).isEqualTo(
                    requestPage + oneIndexedParameter),
                () -> assertThat(studylogsResponse.getTotalSize()).isEqualTo(findElementCounts),
                () -> assertThat(studylogsResponse.getTotalPage()).isEqualTo(
                    (findElementCounts / pageSize) + 1),
                () -> assertThat(studylogsResponse.getData()).hasSize(1)
            );
        }

        @DisplayName("StudyLog 검색 요청에 1. id가 없고, 2. 키워드가 있다면 키워드를 기반으로 StudyLog 를 특정한다.")
        @Test
        void findStudylogs_findByKeyword() {
            //given
            final int requestPage = 0;
            final int pageSize = 10;
            final Pageable pageableRequest = PageRequest.of(requestPage, pageSize);
            final StudylogsSearchRequest studylogsSearchRequest = new StudylogsSearchRequest(
                "키워드",
                null,
                null,
                null,
                null, null,
                null,
                null,
                null,
                pageableRequest
            );

            given(studylogDocumentService.findBySearchKeyword(any(), any(), any(), any(), any(),
                any(), any(), any()))
                .willReturn(StudylogDocumentResponse.of(Arrays.asList(1L), 1, 1, 0));

            final int findElementCounts = 1;
            given(studylogRepository.findByIdInAndDeletedFalseOrderByIdDesc(anyList()))
                .willReturn(Arrays.asList(studylog));

            given(commentRepository.countByStudylogIn(anyList()))
                .willReturn(Arrays.asList(
                        new CommentCount(studylog, 1L)
                    )
                );

            //when
            final StudylogsResponse studylogsResponse = studylogService.findStudylogs(
                studylogsSearchRequest, 1L);
            int oneIndexedParameter = 1;

            //then
            assertAll(
                () -> assertThat(studylogsResponse.getCurrPage()).isEqualTo(
                    requestPage + oneIndexedParameter),
                () -> assertThat(studylogsResponse.getTotalSize()).isEqualTo(findElementCounts),
                () -> assertThat(studylogsResponse.getTotalPage()).isEqualTo(
                    (findElementCounts / pageSize) + 1),
                () -> assertThat(studylogsResponse.getData()).hasSize(1)
            );
        }
    }

    @DisplayName("Studylog Session을 수정할 수 있다.")
    @Test
    void updateStudylogSession() {
        //given
        final long memberId = 1L;
        final long studylogId = 3L;
        final String title = "제목";
        final String content = "내용";
        final Tags tags = Tags.of(Collections.singletonList("스터디로그"));
        final StudylogSessionRequest studylogSessionRequest =
            new StudylogSessionRequest(2L);

        final Session updatedSession = new Session(5L, "변경된 세션");
        final Session session = new Session(3L, "기존 세션");

        final Studylog studylog = new Studylog(
            new Member(1L, "username", "nickname", Role.CREW, 1L, "image"),
            title,
            content,
            session,
            null,
            tags.getList()
        );

        when(studylogRepository.findById(anyLong()))
            .thenReturn(Optional.of(studylog));

        when(sessionService.findSessionById(anyLong()))
            .thenReturn(Optional.of(updatedSession));

        //when
        studylogService.updateStudylogSession(memberId, studylogId, studylogSessionRequest);

        //then
        assertAll(
            () -> assertEquals(updatedSession.getCurriculumId(),
                studylog.getSession().getCurriculumId()),
            () -> assertEquals(updatedSession.getName(), studylog.getSession().getName())
        );
    }

    @DisplayName("studylog 를 수정할 수 있다.")
    @Test
    void updateStudylog() {
        //given
        final long memberId = 1L;
        final long studylogId = 3L;
        final String title = "제목";
        final String content = "내용";
        final Tags tags = Tags.of(Collections.singletonList("스터디로그"));
        final Tags newTags = Tags.of(Collections.singletonList("새로운 스터티로그"));
        final List<TagRequest> tagRequests = Collections.singletonList(new TagRequest("스터디로그"));
        final Member member = new Member(1L, "username", "nickname", Role.CREW, 1L, "image");
        final Session session = new Session(3L, "기존 세션");
        final Mission mission = new Mission("기존 미션", session);

        final StudylogRequest studylogRequest =
            new StudylogRequest(
                "변경된 제목",
                "변경된 내용",
                3L,
                5L,
                tagRequests, null
            );

        final Studylog studylog = new Studylog(
            member,
            title,
            content,
            session,
            null,
            tags.getList()
        );

        when(studylogRepository.findById(anyLong()))
            .thenReturn(Optional.of(studylog));

        when(sessionService.findSessionById(anyLong()))
            .thenReturn(Optional.of(session));

        when(missionService.findMissionById(anyLong()))
            .thenReturn(Optional.of(mission));

        when(memberService.findById(anyLong()))
            .thenReturn(member);

        when(tagService.findByStudylogsAndMember(any(), any()))
            .thenReturn(tags);

        when(tagService.findOrCreate(any()))
            .thenReturn(newTags);

        doNothing().when(answerService).updateAnswers(any(), any());

        //when
        studylogService.updateStudylog(
            memberId,
            studylogId,
            studylogRequest
        );

        //then
        verify(memberTagService, times(1))
            .updateMemberTag(any(), any(), any());

        verify(studylogDocumentService, times(1))
            .update(any());

        assertAll(
            () -> assertEquals(studylogRequest.getTitle(), studylog.getTitle()),
            () -> assertEquals(studylogRequest.getContent(), studylog.getContent()),
            () -> assertEquals(session.getName(), studylog.getSession().getName()),
            () -> assertEquals(session.getCurriculumId(),
                studylog.getSession().getCurriculumId()),
            () -> assertEquals(mission.getName(), studylog.getMission().getName())
        );
    }

    @DisplayName("임시저장된 studylog를 찾을 수 있다.")
    @Test
    void findStudylogTemp() {
        //given
        final String title = "제목";
        final String content = "내용";
        final Tags tags = Tags.of(Collections.singletonList("스터디로그"));
        final Member member = new Member(1L, "username", "nickname", Role.CREW, 1L, "image");
        final Session session = new Session(3L, "기존 세션");
        final Mission mission = new Mission("기존 미션", session);

        final StudylogTemp studylogTemp = new StudylogTemp(
            member,
            title,
            content,
            session,
            mission,
            tags.getList()
        );

        when(studylogTempRepository.existsByMemberId(anyLong()))
            .thenReturn(true);

        when(studylogTempRepository.findByMemberId(anyLong()))
            .thenReturn(studylogTemp);

        when(answerService.findAnswersTempByMemberId(any()))
            .thenReturn(Collections.emptyList());

        //when
        final StudylogTempResponse studylogTempResponse = studylogService.findStudylogTemp(1L);

        //then
        assertAll(
            () -> assertEquals(studylogTempResponse.getTitle(), studylogTemp.getTitle()),
            () -> assertEquals(studylogTempResponse.getId(), studylogTemp.getId()),
            () -> assertEquals(studylogTempResponse.getContent(), studylogTemp.getContent())
        );
    }

    @Nested
    class integrate {

        private Studylog studylog;
        private Member judy;
        private Session session;
        private Mission mission;

        @BeforeEach
        void setUp() {
            judy = new Member(1L, "xrabcde", "바다", Role.CREW, 1111L,
                "https://avatars.githubusercontent.com/u/56033755?v=4");
            session = new Session("세션1");
            mission = new Mission("미션", session);
            studylog = new Studylog(judy, "제목", "내용", session, mission, new ArrayList<>());
        }

        @DisplayName("findReadIds() : 멤버가 읽은 studylog들의 id들을 가져온다")
        @Test
        void findReadIds() throws NoSuchFieldException, IllegalAccessException {
            //given
            given(studylogReadRepository.findByMemberId(anyLong()))
                .willReturn(Arrays.asList(
                    new StudylogRead(null, makeStudyLogFor(1L)),
                    new StudylogRead(null, makeStudyLogFor(2L))
                ));

            //when
            List<Long> actual = studylogService.findReadIds(judy.getId());

            //then
            assertThat(actual).containsExactly(1L, 2L);
        }

        private Studylog makeStudyLogFor(final long id) {
            final Studylog idInjectedStudylog = new Studylog(judy, "제목", "내용", session, mission,
                new ArrayList<>());
            try {
                final Field idField = idInjectedStudylog.getClass().getDeclaredField("id");
                idField.setAccessible(true);
                idField.set(idInjectedStudylog, id);
                return idInjectedStudylog;
            } catch (NoSuchFieldException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }

        @DisplayName("updateRead() : studylog를 읽은 상태로 바꾼다")
        @Test
        void updateRead() {
            //given
            List<Long> readIds = Arrays.asList(1L, 2L);
            final List<StudylogResponse> studylogResponses = new ArrayList<>();
            studylogResponses.addAll(makeStudylogResponseFor(1L, 11));
            studylogResponses.addAll(makeStudylogResponseFor(2L, 33));
            studylogResponses.addAll(makeStudylogResponseFor(3L, 55));

            //when
            studylogService.updateRead(studylogResponses, readIds);

            //then
            assertThat(studylogResponses)
                .filteredOn((studylogResponse) -> checkReadTrue(studylogResponse))
                .hasSize(44);
        }

        private List<StudylogResponse> makeStudylogResponseFor(final long id, final int times) {
            return Stream.generate(() -> new StudylogResponse(id,
                    null, null, null, null, null, null, null, null, null, false, false, 0, false, 0, 0))
                .limit(times)
                .collect(Collectors.toList());
        }

        private boolean checkReadTrue(StudylogResponse studylogResponse) {
            try {
                final Field readField = studylogResponse.getClass().getDeclaredField("read");
                readField.setAccessible(true);
                return readField.getBoolean(studylogResponse);
            } catch (NoSuchFieldException e) {
                return false;
            } catch (IllegalAccessException e) {
                return false;
            }
        }

        @DisplayName("readRssFeeds() : 최신 studylog 50개 조회하고 studylogRssFeedResponse로 변환한다")
        @Test
        void readRssFeeds() {
            //given
            final List<Studylog> studylogs = Stream.generate(() -> makeStudylogWithDateFor(1L))
                .limit(50)
                .collect(Collectors.toList());

            given(studylogRepository.findTop50ByDeletedFalseOrderByIdDesc())
                .willReturn(studylogs);

            //when
            List<StudylogRssFeedResponse> studylogRssFeedResponses = studylogService.readRssFeeds(
                "URI");

            //then
            assertThat(studylogRssFeedResponses).hasSize(50);
        }

        private Studylog makeStudylogWithDateFor(final long id) {
            final Studylog idAndDateInjectedStudylog = new Studylog(judy, "제목", "내용", session,
                mission, new ArrayList<>());
            try {
                final Field idField = idAndDateInjectedStudylog.getClass().getDeclaredField("id");
                idField.setAccessible(true);
                idField.set(idAndDateInjectedStudylog, id);

                final Field createdAtField = idAndDateInjectedStudylog.getClass().getSuperclass()
                    .getDeclaredField("createdAt");
                createdAtField.setAccessible(true);
                createdAtField.set(idAndDateInjectedStudylog, LocalDateTime.now());

                return idAndDateInjectedStudylog;
            } catch (NoSuchFieldException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
