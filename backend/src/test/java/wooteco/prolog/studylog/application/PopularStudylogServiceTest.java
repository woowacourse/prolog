package wooteco.prolog.studylog.application;

import org.junit.jupiter.api.BeforeEach;
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

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;


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
    private List<MemberGroup> memberGroups;
    private List<GroupMember> groupMembers;
    private List<PopularStudylog> popularStudylogs;
    private List<Studylog> studylogs;

    @BeforeEach
    void init() {
        memberGroups = makeMemberGroups();
        split = new Member(1L, "박상현", "스플릿", Role.CREW, 1L, "image url");
        journey = new Member(2L, "이지원", "져니", Role.CREW, 2L, "image url");
        groupMembers = makeBackendGroupMembers();
        studylogs = makeStudyLogs();
        popularStudylogs = makePopularStudyLogs();
    }

    @Test
    void updatePopularStudylogs_allCondition() {
        // given
        final PageRequest pageRequest = PageRequest.of(1, 2);

        // when
        when(groupMemberRepository.findAll()).thenReturn(groupMembers);
        when(memberGroupRepository.findAll()).thenReturn(memberGroups);
        when(studylogRepository.findByPastDays(any())).thenReturn(studylogs);
        when(popularStudylogRepository.findAllByDeletedFalse()).thenReturn(popularStudylogs);
        when(popularStudylogRepository.saveAll(any())).thenReturn(popularStudylogs);

        // then
        assertDoesNotThrow(() -> popularStudylogService.updatePopularStudylogs(pageRequest));
    }

    @Test
    void updatePopularStudylogs_notEnoughWhileTwoCycle() {
        // given
        final PageRequest pageRequest = PageRequest.of(1, 5);

        // when
        when(groupMemberRepository.findAll()).thenReturn(groupMembers);
        when(memberGroupRepository.findAll()).thenReturn(memberGroups);
        when(studylogRepository.findByPastDays(any())).thenReturn(studylogs);
        when(popularStudylogRepository.findAllByDeletedFalse()).thenReturn(popularStudylogs);
        when(popularStudylogRepository.saveAll(any())).thenReturn(popularStudylogs);

        // then
        assertDoesNotThrow(() -> popularStudylogService.updatePopularStudylogs(pageRequest));
    }

    @Test
    void findPopularStudylogs_IsAnonymousMemberTrue() {
        final PageRequest pageRequest = PageRequest.of(0, 1);
        final Page<Studylog> pages = new PageImpl<>(studylogs, pageRequest, 2);

        when(groupMemberRepository.findAll()).thenReturn(groupMembers);
        when(memberGroupRepository.findAll()).thenReturn(memberGroups);
        when(studylogRepository.findAllByIdIn(any(), any())).thenReturn(pages);
        when(popularStudylogRepository.findAllByDeletedFalse()).thenReturn(popularStudylogs);

        // when
        final PopularStudylogsResponse popularStudylogs = popularStudylogService.findPopularStudylogs(
            pageRequest,
            1L,
            true
        );

        // then
        final StudylogsResponse frontEndResponse = popularStudylogs.getFrontResponse();
        final StudylogsResponse backEndResponse = popularStudylogs.getBackResponse();
        final StudylogsResponse androidResponse = popularStudylogs.getAndroidResponse();
        final StudylogsResponse allResponse = popularStudylogs.getAllResponse();

        assertAll(
            () -> assertThat(frontEndResponse.getData()).hasSize(0),
            () -> assertThat(frontEndResponse.getTotalPage()).isEqualTo(0),
            () -> assertThat(frontEndResponse.getCurrPage()).isEqualTo(1),
            () -> assertThat(frontEndResponse.getTotalSize()).isEqualTo(0),

            () -> assertThat(backEndResponse.getData()).hasSize(2),
            () -> assertThat(backEndResponse.getTotalPage()).isEqualTo(2),
            () -> assertThat(backEndResponse.getCurrPage()).isEqualTo(1),
            () -> assertThat(backEndResponse.getTotalSize()).isEqualTo(2),

            () -> assertThat(androidResponse.getData()).hasSize(0),
            () -> assertThat(androidResponse.getTotalPage()).isEqualTo(0),
            () -> assertThat(androidResponse.getCurrPage()).isEqualTo(1),
            () -> assertThat(androidResponse.getTotalSize()).isEqualTo(0),

            () -> assertThat(allResponse.getData()).hasSize(2),
            () -> assertThat(allResponse.getTotalPage()).isEqualTo(2),
            () -> assertThat(allResponse.getCurrPage()).isEqualTo(1),
            () -> assertThat(allResponse.getTotalSize()).isEqualTo(2)
        );
    }

    @Test
    void findPopularStudylogs_IsAnonymousMemberFalse() {
        // given
        final PageRequest pageRequest = PageRequest.of(0, 1);
        Page<Studylog> pages = new PageImpl<>(studylogs, pageRequest, 2);

        when(groupMemberRepository.findAll()).thenReturn(groupMembers);
        when(memberGroupRepository.findAll()).thenReturn(memberGroups);
        when(studylogRepository.findAllByIdIn(any(), any())).thenReturn(pages);
        when(studylogService.findScrapIds(any())).thenReturn(Arrays.asList(1L, 2L));
        doNothing().when(studylogService).updateScrap(any(), any());
        when(studylogService.findReadIds(any())).thenReturn(Arrays.asList(1L, 2L));
        doNothing().when(studylogService).updateRead(any(), any());

        // when
        final PopularStudylogsResponse popularStudylogs = popularStudylogService.findPopularStudylogs(
            pageRequest,
            split.getId(),
            false
        );

        // then
        final StudylogsResponse frontEndResponse = popularStudylogs.getFrontResponse();
        final StudylogsResponse backEndResponse = popularStudylogs.getBackResponse();
        final StudylogsResponse androidResponse = popularStudylogs.getAndroidResponse();
        final StudylogsResponse allResponse = popularStudylogs.getAllResponse();

        assertAll(
            () -> assertThat(frontEndResponse.getData()).hasSize(0),
            () -> assertThat(frontEndResponse.getTotalPage()).isEqualTo(0),
            () -> assertThat(frontEndResponse.getCurrPage()).isEqualTo(1),
            () -> assertThat(frontEndResponse.getTotalSize()).isEqualTo(0),

            () -> assertThat(backEndResponse.getData()).hasSize(2),
            () -> assertThat(backEndResponse.getTotalPage()).isEqualTo(2),
            () -> assertThat(backEndResponse.getCurrPage()).isEqualTo(1),
            () -> assertThat(backEndResponse.getTotalSize()).isEqualTo(2),

            () -> assertThat(androidResponse.getData()).hasSize(0),
            () -> assertThat(androidResponse.getTotalPage()).isEqualTo(0),
            () -> assertThat(androidResponse.getCurrPage()).isEqualTo(1),
            () -> assertThat(androidResponse.getTotalSize()).isEqualTo(0),

            () -> assertThat(allResponse.getData()).hasSize(2),
            () -> assertThat(allResponse.getTotalPage()).isEqualTo(2),
            () -> assertThat(allResponse.getCurrPage()).isEqualTo(1),
            () -> assertThat(allResponse.getTotalSize()).isEqualTo(2)
        );
    }

    private List<MemberGroup> makeMemberGroups() {
        final MemberGroup frontend = new MemberGroup(null, "5기 프론트엔드", "5기 프론트엔드 설명");
        final MemberGroup backend = new MemberGroup(null, "5기 백엔드", "5기 백엔드 설명");
        final MemberGroup android = new MemberGroup(null, "5기 안드로이드", "5기 안드로이드 설명");
        return Arrays.asList(frontend, backend, android);
    }

    private List<GroupMember> makeBackendGroupMembers() {
        final MemberGroup backend = memberGroups.get(1);
        final GroupMember splitGroupMember = new GroupMember(null, split, backend);
        final GroupMember journeyGroupMember = new GroupMember(null, journey, backend);
        return Arrays.asList(splitGroupMember, journeyGroupMember);
    }

    private List<PopularStudylog> makePopularStudyLogs() {
        final Studylog splitStudylog = studylogs.get(0);
        final Studylog journeyStudylog = studylogs.get(1);

        final PopularStudylog popularStudylog1 = new PopularStudylog(1L, splitStudylog.getId(), false);
        final PopularStudylog popularStudylog2 = new PopularStudylog(2L, journeyStudylog.getId(), false);
        return Arrays.asList(popularStudylog1, popularStudylog2);
    }

    private List<Studylog> makeStudyLogs() {
        final Session session = new Session("세션 2");
        final Mission mission = new Mission("자동차 미션", session);

        final Tag tag1 = new Tag("Java");
        final Tag tag2 = new Tag("Spring");
        final List<Tag> tags = Arrays.asList(tag1, tag2);

        final Studylog splitStudylog = new Studylog(split, "제목", "내용", mission, tags);
        final Studylog journeyStudylog = new Studylog(journey, "제목", "내용", mission, tags);
        return Arrays.asList(splitStudylog, journeyStudylog);
    }
}
