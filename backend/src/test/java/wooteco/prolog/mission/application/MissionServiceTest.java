package wooteco.prolog.mission.application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import wooteco.prolog.level.application.LevelService;
import wooteco.prolog.level.application.dto.LevelRequest;
import wooteco.prolog.level.application.dto.LevelResponse;
import wooteco.prolog.level.domain.Level;
import wooteco.prolog.mission.application.dto.MissionRequest;
import wooteco.prolog.mission.application.dto.MissionResponse;
import wooteco.prolog.mission.domain.Mission;
import wooteco.prolog.mission.exception.DuplicateMissionException;
import wooteco.prolog.mission.exception.MissionNotFoundException;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ActiveProfiles("test")
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class MissionServiceTest {

    @Autowired
    private LevelService levelService;
    @Autowired
    private MissionService missionService;

    private Level level1;
    private Level level2;
    private Level level3;

    @BeforeEach
    void setUp() {
        LevelResponse levelResponse1 = levelService.create(new LevelRequest("레벨1"));
        level1 = new Level(levelResponse1.getId(), levelResponse1.getName());
        LevelResponse levelResponse2 = levelService.create(new LevelRequest("레벨2"));
        level2 = new Level(levelResponse2.getId(), levelResponse2.getName());
        LevelResponse levelResponse3 = levelService.create(new LevelRequest("레벨3"));
        level3 = new Level(levelResponse3.getId(), levelResponse3.getName());
    }

    @DisplayName("Mission을 생성한다.")
    @Test
    void createTest() {
        // given
        MissionRequest request = new MissionRequest("레벨1 - 자동차 경주 미션", level1.getId());

        // when
        MissionResponse response = missionService.create(request);

        // then
        assertThat(response.getName()).isEqualTo(request.getName());
        assertThat(response.getLevel().getId()).isEqualTo(request.getLevelId());
    }

    @DisplayName("이미 존재하는 이름으로 Mission 생성시 지정된 예외가 발생한다.")
    @Test
    void createExceptionTest() {
        // given
        MissionRequest request = new MissionRequest("레벨1 - 자동차 경주 미션", level1.getId());
        missionService.create(request);

        // when, then
        assertThatThrownBy(() -> missionService.create(request))
                .isExactlyInstanceOf(DuplicateMissionException.class);
    }

    @DisplayName("ID를 통해서 Mission을 조회한다.")
    @Test
    void findByIdTest() {
        // given
        MissionRequest request = new MissionRequest("레벨1 - 자동차 경주 미션", level1.getId());
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
        MissionResponse mission1 = missionService.create(new MissionRequest("레벨1 - 자동차 경주 미션", level1.getId()));
        MissionResponse mission2 = missionService.create(new MissionRequest("레벨2 - 로또 미션", level2.getId()));
        MissionResponse mission3 = missionService.create(new MissionRequest("레벨3 - 블랙잭 미션", level3.getId()));

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
        MissionResponse mission1 = missionService.create(new MissionRequest("레벨1 - 자동차 경주 미션", level1.getId()));
        MissionResponse mission2 = missionService.create(new MissionRequest("레벨2 - 로또 미션", level2.getId()));
        MissionResponse mission3 = missionService.create(new MissionRequest("레벨3 - 블랙잭 미션", level3.getId()));

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