package wooteco.prolog.studylog.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import wooteco.prolog.member.domain.GroupMember;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.member.domain.MemberGroup;
import wooteco.prolog.member.domain.Role;
import wooteco.prolog.member.domain.repository.GroupMemberRepository;
import wooteco.prolog.member.domain.repository.MemberGroupRepository;
import wooteco.prolog.session.domain.Mission;
import wooteco.prolog.session.domain.Session;
import wooteco.prolog.studylog.application.dto.PopularStudylogsResponse;
import wooteco.prolog.studylog.application.dto.StudylogsResponse;
import wooteco.prolog.studylog.domain.PopularStudylog;
import wooteco.prolog.studylog.domain.Studylog;
import wooteco.prolog.studylog.domain.Tag;
import wooteco.prolog.studylog.domain.repository.PopularStudylogRepository;
import wooteco.prolog.studylog.domain.repository.StudylogRepository;

@ExtendWith(MockitoExtension.class)
class PopularStudylogServiceTest {

    @Mock
    private StudylogService studylogService;
    @Mock
    private StudylogRepository studylogRepository;
    @Mock
    private PopularStudylogRepository popularStudylogRepository;
    @Mock
    private MemberGroupRepository memberGroupRepository;
    @Mock
    private GroupMemberRepository groupMemberRepository;
    @InjectMocks
    private PopularStudylogService popularStudylogService;

    private Member split, journey;
    private MemberGroup frontend, backend, android;
    private GroupMember splitGroupMember, journeyGroupMember;
    private Studylog splitStudylog, journeyStudylog;
    private PopularStudylog splitPopularStudylog, journeyPopularStudylog;

    @BeforeEach
    void init() {
        //memberGroup
        frontend = new MemberGroup(null, "5기 프론트엔드", "5기 프론트엔드 설명");
        backend = new MemberGroup(null, "5기 백엔드", "5기 백엔드 설명");
        android = new MemberGroup(null, "5기 안드로이드", "5기 안드로이드 설명");

        //member
        split = new Member(1L, "박상현", "스플릿", Role.CREW, 1L, "image url");
        journey = new Member(2L, "이지원", "져니", Role.CREW, 2L, "image url");

        //groupMember
        splitGroupMember = new GroupMember(null, split, backend);
        journeyGroupMember = new GroupMember(null, journey, backend);

        //studylog
        final Session session = new Session("세션 2");
        final Mission mission = new Mission("자동차 미션", session);
        final Tag tag1 = new Tag("Java");
        final Tag tag2 = new Tag("Spring");
        final List<Tag> tags = Arrays.asList(tag1, tag2);

        splitStudylog = new Studylog(split, "제목", "내용", mission, tags);
        journeyStudylog = new Studylog(journey, "제목", "내용", mission, tags);

        //popularStudylog
        splitPopularStudylog = new PopularStudylog(1L, splitStudylog.getId(), false);
        journeyPopularStudylog = new PopularStudylog(2L, journeyStudylog.getId(), false);
    }

    @DisplayName("2주안에 작성된 인기 학습로그가 페이지에 표시될 수 있는 최대치만큼 존재할 때 인기 학습로그 업데이트를 테스트한다.")
    @Test
    void enoughWhileTwoCycle() {
        //given
        final PageRequest pageRequest = PageRequest.of(1, 2);
        final List<MemberGroup> memberGroups = Arrays.asList(frontend, backend, android);
        final List<GroupMember> groupMembers = Arrays.asList(splitGroupMember,
            journeyGroupMember);
        final List<Studylog> studylogs = Arrays.asList(splitStudylog, journeyStudylog);
        final List<PopularStudylog> popularStudylogs = Arrays.asList(splitPopularStudylog,
            journeyPopularStudylog);

        when(groupMemberRepository.findAll()).thenReturn(groupMembers);
        when(memberGroupRepository.findAll()).thenReturn(memberGroups);
        when(studylogRepository.findByPastDays(any())).thenReturn(studylogs);
        when(popularStudylogRepository.findAllByDeletedFalse()).thenReturn(popularStudylogs);
        when(popularStudylogRepository.saveAll(any())).thenReturn(popularStudylogs);

        //when, then
        assertDoesNotThrow(() -> popularStudylogService.updatePopularStudylogs(pageRequest));
    }

    @DisplayName("2주동안 작성된 인기 학습로그가 페이지에 출력할 만큼 존재하지 않을 때 인기 학습로그 업데이트를 테스트한다.")
    @Test
    void notEnoughWhileTwoCycle() {
        //given
        final PageRequest pageRequest = PageRequest.of(1, 5);
        final List<MemberGroup> memberGroups = Arrays.asList(frontend, backend, android);
        final List<GroupMember> groupMembers = Arrays.asList(splitGroupMember,
            journeyGroupMember);
        final List<Studylog> studylogs = Arrays.asList(splitStudylog, journeyStudylog);
        final List<PopularStudylog> popularStudylogs = Arrays.asList(splitPopularStudylog,
            journeyPopularStudylog);

        when(groupMemberRepository.findAll()).thenReturn(groupMembers);
        when(memberGroupRepository.findAll()).thenReturn(memberGroups);
        when(studylogRepository.findByPastDays(any())).thenReturn(studylogs);
        when(popularStudylogRepository.findAllByDeletedFalse()).thenReturn(popularStudylogs);
        when(popularStudylogRepository.saveAll(any())).thenReturn(popularStudylogs);

        //when, then
        assertDoesNotThrow(() -> popularStudylogService.updatePopularStudylogs(pageRequest));
    }


    @DisplayName("익명의 사용자일 경우 스크랩과 읽음 여부가 표시하지 않고 학습로그를 조회한다.")
    @Test
    void findPopularStudylogs_IsAnonymousMemberTrue() {
        final List<Studylog> studylogs = Arrays.asList(splitStudylog, journeyStudylog);
        final PageRequest pageRequest = PageRequest.of(0, 1);
        final Page<Studylog> pages = new PageImpl<>(studylogs, pageRequest, 2);
        final List<MemberGroup> memberGroups = Arrays.asList(frontend, backend, android);
        final List<GroupMember> groupMembers = Arrays.asList(splitGroupMember,
            journeyGroupMember);
        final List<PopularStudylog> popularStudylogs = Arrays.asList(splitPopularStudylog,
            journeyPopularStudylog);

        when(groupMemberRepository.findAll()).thenReturn(groupMembers);
        when(memberGroupRepository.findAll()).thenReturn(memberGroups);
        when(studylogRepository.findAllByIdIn(any(), any())).thenReturn(pages);
        when(popularStudylogRepository.findAllByDeletedFalse()).thenReturn(popularStudylogs);

        //when
        final PopularStudylogsResponse popularStudylogsResponse = popularStudylogService.findPopularStudylogs(
            pageRequest,
            1L,
            true
        );

        //then
        final StudylogsResponse frontEndResponse = popularStudylogsResponse.getFrontResponse();
        final StudylogsResponse backEndResponse = popularStudylogsResponse.getBackResponse();
        final StudylogsResponse androidResponse = popularStudylogsResponse.getAndroidResponse();
        final StudylogsResponse allResponse = popularStudylogsResponse.getAllResponse();

        assertAll(
            () -> assertThat(frontEndResponse.getData()).isEmpty(),
            () -> assertThat(frontEndResponse.getTotalPage()).isZero(),
            () -> assertThat(frontEndResponse.getCurrPage()).isOne(),
            () -> assertThat(frontEndResponse.getTotalSize()).isZero(),

            () -> assertThat(backEndResponse.getData()).hasSize(2),
            () -> assertThat(backEndResponse.getTotalPage()).isEqualTo(2),
            () -> assertThat(backEndResponse.getCurrPage()).isOne(),
            () -> assertThat(backEndResponse.getTotalSize()).isEqualTo(2),

            () -> assertThat(androidResponse.getData()).isEmpty(),
            () -> assertThat(androidResponse.getTotalPage()).isZero(),
            () -> assertThat(androidResponse.getCurrPage()).isOne(),
            () -> assertThat(androidResponse.getTotalSize()).isZero(),

            () -> assertThat(allResponse.getData()).hasSize(2),
            () -> assertThat(allResponse.getTotalPage()).isEqualTo(2),
            () -> assertThat(allResponse.getCurrPage()).isOne(),
            () -> assertThat(allResponse.getTotalSize()).isEqualTo(2)
        );
    }

    @DisplayName("로그인한 멤버일 경우 스크랩과 읽음 여부가 표시하여 학습로그를 조회한다.")
    @Test
    void findPopularStudylogs_IsAnonymousMemberFalse() {
        //given
        final List<Studylog> studylogs = Arrays.asList(splitStudylog, journeyStudylog);
        final PageRequest pageRequest = PageRequest.of(0, 1);
        final Page<Studylog> pages = new PageImpl<>(studylogs, pageRequest, 2);
        final List<MemberGroup> memberGroups = Arrays.asList(frontend, backend, android);
        final List<GroupMember> groupMembers = Arrays.asList(splitGroupMember,
            journeyGroupMember);

        when(groupMemberRepository.findAll()).thenReturn(groupMembers);
        when(memberGroupRepository.findAll()).thenReturn(memberGroups);
        when(studylogRepository.findAllByIdIn(any(), any())).thenReturn(pages);
        when(studylogService.findScrapIds(any())).thenReturn(Arrays.asList(1L, 2L));
        doNothing().when(studylogService).updateScrap(any(), any());
        when(studylogService.findReadIds(any())).thenReturn(Arrays.asList(1L, 2L));
        doNothing().when(studylogService).updateRead(any(), any());

        //when
        final PopularStudylogsResponse popularStudylogs = popularStudylogService.findPopularStudylogs(
            pageRequest,
            split.getId(),
            false
        );

        //then
        final StudylogsResponse frontEndResponse = popularStudylogs.getFrontResponse();
        final StudylogsResponse backEndResponse = popularStudylogs.getBackResponse();
        final StudylogsResponse androidResponse = popularStudylogs.getAndroidResponse();
        final StudylogsResponse allResponse = popularStudylogs.getAllResponse();

        assertAll(
            () -> assertThat(frontEndResponse.getData()).isEmpty(),
            () -> assertThat(frontEndResponse.getTotalPage()).isZero(),
            () -> assertThat(frontEndResponse.getCurrPage()).isOne(),
            () -> assertThat(frontEndResponse.getTotalSize()).isZero(),

            () -> assertThat(backEndResponse.getData()).hasSize(2),
            () -> assertThat(backEndResponse.getTotalPage()).isEqualTo(2),
            () -> assertThat(backEndResponse.getCurrPage()).isOne(),
            () -> assertThat(backEndResponse.getTotalSize()).isEqualTo(2),

            () -> assertThat(androidResponse.getData()).isEmpty(),
            () -> assertThat(androidResponse.getTotalPage()).isZero(),
            () -> assertThat(androidResponse.getCurrPage()).isOne(),
            () -> assertThat(androidResponse.getTotalSize()).isZero(),

            () -> assertThat(allResponse.getData()).hasSize(2),
            () -> assertThat(allResponse.getTotalPage()).isEqualTo(2),
            () -> assertThat(allResponse.getCurrPage()).isOne(),
            () -> assertThat(allResponse.getTotalSize()).isEqualTo(2)
        );
    }
}
