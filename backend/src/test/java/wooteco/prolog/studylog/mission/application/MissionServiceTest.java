package wooteco.prolog.studylog.mission.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import wooteco.prolog.session.application.MissionService;
import wooteco.prolog.session.application.SessionService;
import wooteco.prolog.session.application.dto.MissionRequest;
import wooteco.prolog.session.application.dto.MissionResponse;
import wooteco.prolog.session.application.dto.SessionRequest;
import wooteco.prolog.session.application.dto.SessionResponse;
import wooteco.prolog.session.domain.Mission;
import wooteco.prolog.session.domain.Session;
import wooteco.prolog.studylog.exception.DuplicateMissionException;
import wooteco.prolog.studylog.exception.MissionNotFoundException;
import wooteco.support.utils.IntegrationTest;

@IntegrationTest
class MissionServiceTest {

    @Autowired
    private SessionService sessionService;
    @Autowired
    private MissionService missionService;

    private Session session1;
    private Session session2;
    private Session session3;

    @BeforeEach
    void setUp() {
        SessionResponse sessionResponse1 = sessionService.create(new SessionRequest("세션1"));
        session1 = new Session(sessionResponse1.getId(), sessionResponse1.getName());
        SessionResponse sessionResponse2 = sessionService.create(new SessionRequest("세션2"));
        session2 = new Session(sessionResponse2.getId(), sessionResponse2.getName());
        SessionResponse sessionResponse3 = sessionService.create(new SessionRequest("세션3"));
        session3 = new Session(sessionResponse3.getId(), sessionResponse3.getName());
    }

    @DisplayName("Mission을 생성한다.")
    @Test
    void createTest() {
        // given
        MissionRequest request = new MissionRequest("세션1 - 자동차 경주 미션", session1.getId());

        // when
        MissionResponse response = missionService.create(request);

        // then
        assertThat(response.getName()).isEqualTo(request.getName());
        assertThat(response.getSession().getId()).isEqualTo(request.getSessionId());
    }

    @DisplayName("이미 존재하는 이름으로 Mission 생성시 지정된 예외가 발생한다.")
    @Test
    void createExceptionTest() {
        // given
        MissionRequest request = new MissionRequest("세션1 - 자동차 경주 미션", session1.getId());
        missionService.create(request);

        // when, then
        assertThatThrownBy(() -> missionService.create(request))
            .isExactlyInstanceOf(DuplicateMissionException.class);
    }

    @DisplayName("ID를 통해서 Mission을 조회한다.")
    @Test
    void findByIdTest() {
        // given
        MissionRequest request = new MissionRequest("세션1 - 자동차 경주 미션", session1.getId());
        MissionResponse savedMission = missionService.create(request);

        // when
        Mission foundMission = missionService.findById(savedMission.getId());

        // then
        assertThat(foundMission).usingRecursiveComparison()
            .isEqualTo(savedMission);
    }

    @DisplayName("ID를 통해서 Mission 조회 실패시 지정된 예외가 발생한다.")
    @Test
    void findByIdExceptionTest() {
        // when, then
        assertThatThrownBy(() -> missionService.findById(999L))
            .isExactlyInstanceOf(MissionNotFoundException.class);
    }

    @DisplayName("ID 리스트를 통해서 Mission 리스트를 조회한다.")
    @Test
    void findByIdsTest() {
        // given
        MissionResponse mission1 = missionService
            .create(new MissionRequest("세션1 - 자동차 경주 미션", session1.getId()));
        MissionResponse mission2 = missionService
            .create(new MissionRequest("세션2 - 로또 미션", session2.getId()));
        MissionResponse mission3 = missionService
            .create(new MissionRequest("세션3 - 블랙잭 미션", session3.getId()));

        List<Long> missionIds = Arrays.asList(mission1.getId(), mission2.getId(), mission3.getId());

        // when
        List<Mission> missions = missionService.findByIds(missionIds);

        // then
        assertThat(missions.size()).isEqualTo(3);
    }

    @DisplayName("ID 리스트를 통한 Mission 조회 결과가 없을 경우 빈 리스트를 받는다.")
    @Test
    void findByIdsEmptyTest() {
        // given
        List<Long> missionIds = Arrays.asList(1L, 2L, 3L);

        // when
        List<Mission> missions = missionService.findByIds(missionIds);

        // then
        assertThat(missions).isEmpty();
    }

    @DisplayName("모든 MissionResponse를 조회한다.")
    @Test
    void findAllTest() {
        // given
        MissionResponse mission1 = missionService
            .create(new MissionRequest("세션1 - 자동차 경주 미션", session1.getId()));
        MissionResponse mission2 = missionService
            .create(new MissionRequest("세션2 - 로또 미션", session2.getId()));
        MissionResponse mission3 = missionService
            .create(new MissionRequest("세션3 - 블랙잭 미션", session3.getId()));

        // when
        List<MissionResponse> responses = missionService.findAll();

        // then
        assertThat(responses.size()).isEqualTo(3);
    }

    @DisplayName("MissionResponse 조회 결과가 없을 경우 빈 리스트를 받는다.")
    @Test
    void findAllEmptyTest() {
        // when
        List<MissionResponse> responses = missionService.findAll();

        // then
        assertThat(responses).isEmpty();
    }
}
