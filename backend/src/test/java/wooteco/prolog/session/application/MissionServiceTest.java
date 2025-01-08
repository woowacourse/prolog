package wooteco.prolog.session.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static wooteco.prolog.login.ui.LoginMember.Authority.ANONYMOUS;
import static wooteco.prolog.login.ui.LoginMember.Authority.MEMBER;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import wooteco.prolog.common.exception.BadRequestException;
import wooteco.prolog.login.ui.LoginMember;
import wooteco.prolog.session.application.dto.MissionRequest;
import wooteco.prolog.session.application.dto.MissionResponse;
import wooteco.prolog.session.domain.Mission;
import wooteco.prolog.session.domain.Session;
import wooteco.prolog.session.domain.repository.MissionRepository;

@ExtendWith(MockitoExtension.class)
class MissionServiceTest {

    @InjectMocks
    private MissionService missionService;

    @Mock
    private SessionService sessionService;

    @Mock
    private MissionRepository missionRepository;

    @DisplayName("Mission을 생성해야 한다.")
    @Test
    void create() {
        // given
        final Session session = new Session("세션");
        final MissionRequest request = new MissionRequest("미션", 1L);
        doReturn(session).when(sessionService).findById(request.getSessionId());
        doReturn(new Mission("베베", session)).when(missionRepository)
            .save(new Mission("베베", session));

        // when
        final MissionResponse response = missionService.create(request);

        // then
        assertAll(
            () -> assertThat(response.getName()).isEqualTo("베베"),
            () -> assertThat(response.getSession().getId()).isEqualTo(session.getId()),
            () -> assertThat(response.getId()).isEqualTo(session.getId())
        );
    }

    @DisplayName("Mission 생성시 Validate")
    @Test
    void validateName() {
        // given
        final MissionRequest request = new MissionRequest("mission1", 1L);
        final Optional<Mission> mission = Optional.of(
            new Mission("mission1", new Session("session1")));

        doReturn(mission)
            .when(missionRepository).findByName(request.getName());

        // when, then
        assertAll(
            () -> assertThrows(BadRequestException.class, () -> missionService.create(request))
        );
    }

    @DisplayName("모든 미션들을 MissionResponse 형태로 조회해야 한다.")
    @Test
    void findAll() {
        // given
        final List<Mission> missions = new ArrayList<>();
        missions.addAll(List.of(new Mission("mission1", new Session("session1"))));
        missions.addAll(List.of(new Mission("mission2", new Session("session2"))));
        missions.addAll(List.of(new Mission("mission3", new Session("session3"))));
        doReturn(missions).when(missionRepository).findAll();

        // when
        final List<MissionResponse> responses = missionService.findAll();

        // then
        assertAll(
            () -> assertThat(responses).hasSize(3),

            () -> assertThat(responses).extracting(MissionResponse::getName)
                .containsExactly("mission1", "mission2", "mission3"),

            () -> assertThat(responses).extracting(response -> response.getSession().getName())
                .containsExactly("session1", "session2", "session3")
        );

    }

    @DisplayName("아이디로 Mission 객체를 조회한다.")
    @Test
    void findById() {
        // given
        final Optional<Mission> missionOptional = Optional.of(
            new Mission("mission1", new Session("session1")));
        when(missionRepository.findById(1L)).thenReturn(missionOptional);

        // when
        final Mission mission = missionService.findById(1L);

        // then
        assertAll(
            () -> assertThat(mission.getName()).isEqualTo("mission1"),
            () -> assertThat(mission.getSession().getName()).isEqualTo("session1")
        );
    }

    @DisplayName("아이디로 Optional<Mission> 객체를 조회한다.")
    @Test
    void findMissionById() {
        // given
        final Optional<Mission> missionOptional = Optional.of(
            new Mission("mission1", new Session("session1")));
        when(missionRepository.findById(1L)).thenReturn(missionOptional);

        // when
        final Optional<Mission> missionById = missionService.findMissionById(1L);

        // then
        Assertions.assertThat(missionById.isPresent()).isTrue();
    }

    @DisplayName("아이디로 Optional<Mission> 객체를 조회한다.")
    @Test
    void findMissionByIdReturnEmpty() {
        // when
        final Optional<Mission> missionById = missionService.findMissionById(1L);

        // then
        Assertions.assertThat(missionById.isPresent()).isFalse();
    }

    @DisplayName("mission의 id들로 Mission들을 조회한다.")
    @Test
    void findByIds() {
        // given
        final List<Mission> missions = new ArrayList<>();
        missions.add(new Mission("mission1", new Session("session1")));
        missions.add(new Mission("mission2", new Session("session2")));
        missions.add(new Mission("mission3", new Session("session3")));

        final List<Long> missionIds = new ArrayList<>();
        missionIds.add(1L);
        missionIds.add(2L);
        missionIds.add(3L);

        doReturn(missions).when(missionRepository).findAllById(new ArrayList<>());

        // when
        final List<Mission> byIds = missionService.findByIds(new ArrayList<>());

        // then
        assertAll(
            () -> assertThat(byIds).extracting(Mission::getName)
                .containsExactly("mission1", "mission2", "mission3")
        );
    }

    @DisplayName("미션을 loginMember로 조회한다.")
    @Test
    void findMyMissions() {
        // given
        final LoginMember loginMember = new LoginMember();
        final List<Long> mySessionIds = new ArrayList<>();
        mySessionIds.add(1L);

        final List<Mission> missions = new ArrayList<>();
        missions.add(new Mission("mission1", new Session("session1")));

        when(sessionService.findMySessionIds(loginMember.getId())).thenReturn(mySessionIds);
        when(missionRepository.findBySessionIdIn(mySessionIds)).thenReturn(missions);

        // when
        final List<MissionResponse> myMissions = missionService.findMyMissions(loginMember);
        final MissionResponse response = myMissions.get(0);

        // then
        assertAll(
            () -> assertThat(response.getName()).isEqualTo("mission1"),
            () -> assertThat(response.getSession().getName()).isEqualTo("session1")
        );
    }

    @DisplayName("LoginMember와 관련된 Session과 Mission을 목록 상단에 보여주도록 정렬한다.(LoginMember의 Mission과 Session이 최상단에 위치)")
    @Test
    void findAllWithMyMissionFirst() {
        // given
        final LoginMember loginMember = new LoginMember(MEMBER);
        final Mission mission1 = new Mission("mission1", new Session("session1"));
        final Mission mission2 = new Mission("mission2", new Session("session2"));
        final Mission mission3 = new Mission("mission3", new Session("session3"));

        final List<Mission> allMissions = new ArrayList<>();
        allMissions.add(mission1);
        allMissions.add(mission2);
        allMissions.add(mission3);
        doReturn(allMissions).when(missionRepository).findAll();

        final List<Mission> myMissions = new ArrayList<>();
        myMissions.add(mission3);
        doReturn(myMissions).when(missionRepository).findBySessionIdIn(Collections.emptyList());

        // when
        final List<MissionResponse> responses = missionService.findAllWithMyMissionFirst(
            loginMember);
        final MissionResponse myMission = responses.get(0);

        // then
        assertAll(
            () -> assertThat(myMission.getName()).isEqualTo("mission3"),
            () -> assertThat(myMission.getSession().getName()).isEqualTo("session3"),
            () -> assertThat(responses).extracting(MissionResponse::getName)
                .contains("mission1", "mission2", "mission3"),
            () -> assertThat(responses).extracting(response -> response.getSession().getName())
                .contains("session1", "session2", "session3")
        );
    }

    @DisplayName("LoginMember의 Authority가 Anonymous일 때 모든 Mission을 반환한다.")
    @Test
    void findAllWithMyMissionFirstReturnFindAll() {
        // given
        final LoginMember loginMember = new LoginMember(ANONYMOUS);
        final Mission mission1 = new Mission("mission1", new Session("session1"));
        final Mission mission2 = new Mission("mission2", new Session("session2"));
        final Mission mission3 = new Mission("mission3", new Session("session3"));

        final List<Mission> allMissions = new ArrayList<>();
        allMissions.add(mission1);
        allMissions.add(mission2);
        allMissions.add(mission3);
        doReturn(allMissions).when(missionRepository).findAll();

        // when
        final List<MissionResponse> responses = missionService.findAllWithMyMissionFirst(
            loginMember);
        final MissionResponse firstResponse = responses.get(0);

        // then
        assertAll(
            () -> assertThat(firstResponse.getName()).isEqualTo("mission1"),
            () -> assertThat(firstResponse.getSession().getName()).isEqualTo("session1"),
            () -> assertThat(responses).extracting(MissionResponse::getName)
                .contains("mission1", "mission2", "mission3"),
            () -> assertThat(responses).extracting(response -> response.getSession().getName())
                .contains("session1", "session2", "session3"),
            () -> assertThat(responses).hasSize(3)
        );
    }

}
