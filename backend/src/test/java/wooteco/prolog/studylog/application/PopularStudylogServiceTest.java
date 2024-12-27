package wooteco.prolog.studylog.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import wooteco.prolog.member.domain.Department;
import wooteco.prolog.member.domain.DepartmentMember;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.member.domain.Role;
import wooteco.prolog.member.domain.repository.DepartmentMemberRepository;
import wooteco.prolog.member.domain.repository.DepartmentRepository;
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
    private DepartmentRepository departmentRepository;
    @Mock
    private DepartmentMemberRepository departmentMemberRepository;
    @InjectMocks
    private PopularStudylogService popularStudylogService;

    @DisplayName("1주안에 작성된 인기 학습로그가 페이지에 표시될 수 있는 최대치만큼 존재할 때 3 그룹에서 한번만 조회한다.")
    @Test
    void enoughWhileTwoCycle() {
        //given
        final Department frontend = setUpDepartment("프론트엔드", "5기");
        final Department backend = setUpDepartment("백엔드", "5기");
        final Department android = setUpDepartment("안드로이드", "5기");

        final Member split = setUpMember(1L, "박상현", "스플릿", 1L);
        final Member journey = setUpMember(2L, "이지원", "져니", 2L);

        final DepartmentMember splitDepartmentMember = setUpDepartmentMember(split, backend);
        final DepartmentMember journeyDepartmentMember = setUpDepartmentMember(journey, backend);

        final Studylog splitStudyLog = setUpStudyLog(split);
        final Studylog journeyStudylog = setUpStudyLog(journey);

        final PopularStudylog splitPopularStudylog = setUpPopularStudylog(1L,
            splitStudyLog.getId());
        final PopularStudylog journeyPopularStudylog = setUpPopularStudylog(2L,
            journeyStudylog.getId());

        final PageRequest pageRequest = PageRequest.of(1, 2);
        final List<Department> departments = Arrays.asList(frontend, backend, android);
        final List<DepartmentMember> departmentMembers = Arrays.asList(splitDepartmentMember,
            journeyDepartmentMember);

        final List<Studylog> studylogs = Arrays.asList(splitStudyLog, journeyStudylog);
        final List<PopularStudylog> popularStudylogs = Arrays.asList(splitPopularStudylog,
            journeyPopularStudylog);

        when(departmentMemberRepository.findAll()).thenReturn(departmentMembers);
        when(departmentRepository.findAll()).thenReturn(departments);
        when(studylogRepository.findByPastDays(any())).thenReturn(studylogs);
        when(popularStudylogRepository.findAllByDeletedFalse()).thenReturn(popularStudylogs);
        when(popularStudylogRepository.saveAll(any())).thenReturn(popularStudylogs);

        //when
        popularStudylogService.updatePopularStudylogs(pageRequest);

        //then
        verify(studylogRepository, times(1)).findByPastDays(any());
    }

    @DisplayName("2주동안 작성된 인기 학습로그가 페이지에 출력할 만큼 존재하지 않을 때 각 3그룹별로 3번까지만 조회한다.")
    @Test
    void notEnoughWhileTwoCycle() {
        //given
        final Department frontend = setUpDepartment("프론트엔드", "5기");
        final Department backend = setUpDepartment("백엔드", "5기");
        final Department android = setUpDepartment("안드로이드", "5기");

        final Member split = setUpMember(1L, "박상현", "스플릿", 1L);
        final Member journey = setUpMember(2L, "이지원", "져니", 2L);

        final DepartmentMember splitDepartmentMember = setUpDepartmentMember(split, backend);
        final DepartmentMember journeyDepartmentMember = setUpDepartmentMember(journey, backend);

        final Studylog splitStudylog = setUpStudyLog(split);
        final Studylog journeyStudylog = setUpStudyLog(journey);

        final PopularStudylog splitPopularStudylog = setUpPopularStudylog(1L,
            splitStudylog.getId());
        final PopularStudylog journeyPopularStudylog = setUpPopularStudylog(2L,
            journeyStudylog.getId());

        final PageRequest pageRequest = PageRequest.of(1, 5);
        final List<Department> departments = Arrays.asList(frontend, backend, android);
        final List<DepartmentMember> departmentMembers = Arrays.asList(splitDepartmentMember,
            journeyDepartmentMember);
        final List<Studylog> studylogs = Arrays.asList(splitStudylog, journeyStudylog);
        final List<PopularStudylog> popularStudylogs = Arrays.asList(splitPopularStudylog,
            journeyPopularStudylog);

        when(departmentMemberRepository.findAll()).thenReturn(departmentMembers);
        when(departmentRepository.findAll()).thenReturn(departments);
        when(studylogRepository.findByPastDays(any())).thenReturn(studylogs);
        when(popularStudylogRepository.findAllByDeletedFalse()).thenReturn(popularStudylogs);
        when(popularStudylogRepository.saveAll(any())).thenReturn(popularStudylogs);

        //when
        popularStudylogService.updatePopularStudylogs(pageRequest);

        //then
        verify(studylogRepository, times(3)).findByPastDays(any());
    }

    @DisplayName("익명의 사용자일 경우 스크랩과 읽음 여부가 표시하지 않고 학습로그를 조회한다.")
    @Test
    void findPopularStudylogs_IsAnonymousMemberTrue() {
        final Department frontend = setUpDepartment("프론트엔드", "5기");
        final Department backend = setUpDepartment("백엔드", "5기");
        final Department android = setUpDepartment("안드로이드", "5기");

        final Member split = setUpMember(1L, "박상현", "스플릿", 1L);
        final Member journey = setUpMember(2L, "이지원", "져니", 2L);

        final DepartmentMember splitDepartmentMember = setUpDepartmentMember(split, backend);
        final DepartmentMember journeyDepartmentMember = setUpDepartmentMember(journey, backend);

        final Studylog splitStudylog = setUpStudyLog(split);
        final Studylog journeyStudylog = setUpStudyLog(journey);

        final PopularStudylog splitPopularStudylog = setUpPopularStudylog(1L,
            splitStudylog.getId());
        final PopularStudylog journeyPopularStudylog = setUpPopularStudylog(2L,
            journeyStudylog.getId());

        final List<Studylog> studylogs = Arrays.asList(splitStudylog, journeyStudylog);
        final PageRequest pageRequest = PageRequest.of(0, 1);
        final Page<Studylog> pages = new PageImpl<>(studylogs, pageRequest, 2);
        final List<Department> departments = Arrays.asList(frontend, backend, android);
        final List<DepartmentMember> departmentMembers = Arrays.asList(splitDepartmentMember,
            journeyDepartmentMember);
        final List<PopularStudylog> popularStudylogs = Arrays.asList(splitPopularStudylog,
            journeyPopularStudylog);

        when(departmentMemberRepository.findAll()).thenReturn(departmentMembers);
        when(departmentRepository.findAll()).thenReturn(departments);
        when(studylogRepository.findAllByIdIn(any(), any())).thenReturn(pages);
        when(popularStudylogRepository.findAllByDeletedFalse()).thenReturn(popularStudylogs);

        //when
        final PopularStudylogsResponse popularStudylogsResponse = popularStudylogService.findPopularStudylogs(
            pageRequest,
            null,
            true
        );

        //then
        final StudylogsResponse frontEndResponse = popularStudylogsResponse.getFrontResponse();
        final StudylogsResponse backEndResponse = popularStudylogsResponse.getBackResponse();
        final StudylogsResponse androidResponse = popularStudylogsResponse.getAndroidResponse();
        final StudylogsResponse allResponse = popularStudylogsResponse.getAllResponse();

        verify(studylogService, never()).updateRead(any(), any());
        verify(studylogService, never()).updateScrap(any(), any());

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
        {
            //given
            final Department frontend = setUpDepartment("프론트엔드", "5기");
            final Department backend = setUpDepartment("백엔드", "5기");
            final Department android = setUpDepartment("안드로이드", "5기");

            final Member split = setUpMember(1L, "박상현", "스플릿", 1L);
            final Member journey = setUpMember(2L, "이지원", "져니", 2L);

            final DepartmentMember splitDepartmentMember = setUpDepartmentMember(split, backend);
            final DepartmentMember journeyDepartmentMember = setUpDepartmentMember(journey, backend);

            final Studylog splitStudylog = setUpStudyLog(split);
            final Studylog journeyStudylog = setUpStudyLog(journey);

            final List<Studylog> studylogs = Arrays.asList(splitStudylog, journeyStudylog);
            final PageRequest pageRequest = PageRequest.of(0, 1);
            final Page<Studylog> pages = new PageImpl<>(studylogs, pageRequest, 2);
            final List<Department> departments = Arrays.asList(frontend, backend, android);
            final List<DepartmentMember> departmentMembers = Arrays.asList(splitDepartmentMember,
                journeyDepartmentMember);

            when(departmentMemberRepository.findAll()).thenReturn(departmentMembers);
            when(departmentRepository.findAll()).thenReturn(departments);
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

            verify(studylogService, times(4)).updateRead(any(), any());
            verify(studylogService, times(4)).updateScrap(any(), any());

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

    @DisplayName("인기학습 로그를 분야별로 나누어서 반한한다.")
    @Test
    void findPopularStudylogs_filterDepartmentType() {
        {
            //given
            final Department frontend = setUpDepartment("프론트엔드", "5기");
            final Department backend = setUpDepartment("백엔드", "5기");
            final Department android = setUpDepartment("안드로이드", "5기");

            final Member split = setUpMember(1L, "박상현", "스플릿", 1L);
            final Member journey = setUpMember(2L, "이지원", "져니", 2L);
            final Member pooh = setUpMember(3L, "백승준", "푸우", 3L);

            final DepartmentMember splitDepartmentMember = setUpDepartmentMember(split, frontend);
            final DepartmentMember journeyDepartmentMember = setUpDepartmentMember(journey, backend);
            final DepartmentMember poohDepartmentMember = setUpDepartmentMember(pooh, android);

            final Studylog splitStudylog = setUpStudyLog(split);
            final Studylog journeyStudylog = setUpStudyLog(journey);
            final Studylog poohStudylog = setUpStudyLog(pooh);

            final List<Studylog> studylogs = Arrays.asList(splitStudylog, journeyStudylog, poohStudylog);
            final PageRequest pageRequest = PageRequest.of(0, 3);
            final Page<Studylog> pages = new PageImpl<>(studylogs, pageRequest, 1);
            final List<Department> departments = Arrays.asList(frontend, backend, android);
            final List<DepartmentMember> departmentMembers = Arrays.asList(splitDepartmentMember, journeyDepartmentMember, poohDepartmentMember);

            final PopularStudylog splitPopularStudylog = setUpPopularStudylog(1L,
                splitStudylog.getId());
            final PopularStudylog journeyPopularStudylog = setUpPopularStudylog(2L,
                journeyStudylog.getId());
            final PopularStudylog poohPopularStudylog = setUpPopularStudylog(3L,
                poohStudylog.getId());

            final List<PopularStudylog> popularStudylogs = Arrays.asList(splitPopularStudylog,
                journeyPopularStudylog, poohPopularStudylog);

            when(popularStudylogRepository.findAllByDeletedFalse()).thenReturn(popularStudylogs);
            when(departmentMemberRepository.findAll()).thenReturn(departmentMembers);
            when(departmentRepository.findAll()).thenReturn(departments);
            when(studylogRepository.findAllByIdIn(any(), any())).thenReturn(pages);

            //when
            final PopularStudylogsResponse popularStudylogsResponse = popularStudylogService.findPopularStudylogs(
                pageRequest,
                null,
                true
            );

            //then
            final StudylogsResponse frontEndResponse = popularStudylogsResponse.getFrontResponse();
            final StudylogsResponse backEndResponse = popularStudylogsResponse.getBackResponse();
            final StudylogsResponse androidResponse = popularStudylogsResponse.getAndroidResponse();
            final StudylogsResponse allResponse = popularStudylogsResponse.getAllResponse();

            assertAll(
                () -> assertThat(frontEndResponse.getData()).hasSize(1),
                () -> assertThat(frontEndResponse.getTotalPage()).isEqualTo(1),
                () -> assertThat(frontEndResponse.getCurrPage()).isOne(),
                () -> assertThat(frontEndResponse.getTotalSize()).isEqualTo(1),

                () -> assertThat(backEndResponse.getData()).hasSize(1),
                () -> assertThat(backEndResponse.getTotalPage()).isEqualTo(1),
                () -> assertThat(backEndResponse.getCurrPage()).isOne(),
                () -> assertThat(backEndResponse.getTotalSize()).isEqualTo(1),

                () -> assertThat(androidResponse.getData()).hasSize(1),
                () -> assertThat(androidResponse.getTotalPage()).isEqualTo(1),
                () -> assertThat(androidResponse.getCurrPage()).isOne(),
                () -> assertThat(androidResponse.getTotalSize()).isEqualTo(1),

                () -> assertThat(allResponse.getData()).hasSize(3),
                () -> assertThat(allResponse.getTotalPage()).isEqualTo(1),
                () -> assertThat(allResponse.getCurrPage()).isOne(),
                () -> assertThat(allResponse.getTotalSize()).isEqualTo(3)
            );
        }
    }


    private Department setUpDepartment(final String part, final String term) {
        return new Department(null, part, term);
    }

    private Member setUpMember(final Long id, final String userName,
        final String nickname, final Long githubId) {
        return new Member(id, userName, nickname, Role.CREW, githubId, "image url");
    }

    private DepartmentMember setUpDepartmentMember(final Member member, final Department department) {
        return new DepartmentMember(null, member, department);
    }

    private Studylog setUpStudyLog(final Member member) {
        final Session session = new Session("세션 2");
        final Mission mission = new Mission("자동차 미션", session);
        final Tag java = new Tag("Java");
        final Tag spring = new Tag("Spring");
        final List<Tag> tags = Arrays.asList(java, spring);
        return new Studylog(member, "제목", "내용", mission, tags);
    }

    private PopularStudylog setUpPopularStudylog(final Long id, final Long studyLogId) {
        return new PopularStudylog(id, studyLogId, false);
    }
}
