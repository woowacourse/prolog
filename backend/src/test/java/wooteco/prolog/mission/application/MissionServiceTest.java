package wooteco.prolog.mission.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import wooteco.prolog.mission.application.dto.MissionRequest;
import wooteco.prolog.mission.application.dto.MissionResponse;
import wooteco.prolog.mission.domain.Mission;
import wooteco.prolog.mission.domain.repository.MissionRepository;
import wooteco.prolog.mission.exception.DuplicateMissionException;
import wooteco.prolog.mission.exception.MissionNotFoundException;

@ActiveProfiles("test")
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class MissionServiceTest {

    @Autowired
    private MissionService missionService;

    @Autowired
    private MissionRepository missionRepository;

    @DisplayName("Mission을 생성한다.")
    @Test
    void createTest() {
        // given
        MissionRequest request = new MissionRequest("레벨1 - 자동차 경주 미션");

        // when
        MissionResponse response = missionService.create(request);

        // then
        Mission mission = missionRepository.findByName(request.getName())
            .orElseThrow(MissionNotFoundException::new);

        assertThat(mission.getId()).isEqualTo(response.getId());
        assertThat(mission.getName()).isEqualTo(request.getName());
        assertThat(mission.getName()).isEqualTo(response.getName());
    }

    @DisplayName("이미 존재하는 이름으로 Mission 생성시 지정된 예외가 발생한다.")
    @Test
    void createExceptionTest() {
        // given
        MissionRequest request = new MissionRequest("레벨1 - 자동차 경주 미션");
        Mission을_생성한다(request.toEntity());

        // when, then
        assertThatThrownBy(() -> missionService.create(request))
            .isExactlyInstanceOf(DuplicateMissionException.class);
    }

    @DisplayName("ID를 통해서 Mission을 조회한다.")
    @Test
    void findByIdTest() {
        // given
        MissionRequest request = new MissionRequest("레벨1 - 자동차 경주 미션");
        Mission savedMission = Mission을_생성한다(request.toEntity());

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
        Mission mission1 = Mission을_생성한다(new Mission("레벨1 - 자동차 경주 미션"));
        Mission mission2 = Mission을_생성한다(new Mission("레벨2 - 로또 미션"));
        Mission mission3 = Mission을_생성한다(new Mission("레벨3 - 블랙잭 미션"));

        List<Long> missionIds = Arrays.asList(mission1.getId(), mission2.getId(), mission3.getId());

        // when
        List<Mission> missions = missionService.findByIds(missionIds);

        // then
        assertThat(missions).usingFieldByFieldElementComparator()
            .containsExactlyInAnyOrder(mission1, mission2, mission3);
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
        Mission mission1 = Mission을_생성한다(new Mission("레벨1 - 자동차 경주 미션"));
        Mission mission2 = Mission을_생성한다(new Mission("레벨2 - 로또 미션"));
        Mission mission3 = Mission을_생성한다(new Mission("레벨3 - 블랙잭 미션"));

        MissionResponse response1 = MissionResponse.of(mission1);
        MissionResponse response2 = MissionResponse.of(mission2);
        MissionResponse response3 = MissionResponse.of(mission3);

        // when
        List<MissionResponse> responses = missionService.findAll();

        // then
        assertThat(responses).usingFieldByFieldElementComparator()
            .containsExactlyInAnyOrder(response1, response2, response3);
    }

    @DisplayName("MissionResponse 조회 결과가 없을 경우 빈 리스트를 받는다.")
    @Test
    void findAllEmptyTest() {
        // when
        List<MissionResponse> responses = missionService.findAll();

        // then
        assertThat(responses).isEmpty();
    }

    private Mission Mission을_생성한다(Mission mission) {
        return missionRepository.save(mission);
    }
}