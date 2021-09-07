package wooteco.prolog.studylog.mission.domain.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import wooteco.prolog.studylog.domain.Level;
import wooteco.prolog.studylog.domain.Mission;
import wooteco.prolog.studylog.domain.repository.LevelRepository;
import wooteco.prolog.studylog.domain.repository.MissionRepository;

@DataJpaTest
class MissionRepositoryTest {

    @Autowired
    LevelRepository levelRepository;

    @Autowired
    MissionRepository missionRepository;

    private Level level;

    @BeforeEach
    void setUp() {
        level = levelRepository.save(new Level("레벨1"));
    }

    @DisplayName("Mission 생성")
    @Test
    void createMission() {
        // given
        Mission mission = new Mission("[BE] 자동차 미션", level);

        // when
        Mission savedMission = missionRepository.save(mission);

        // then
        assertThat(savedMission.getId()).isNotNull();
        assertThat(savedMission).usingRecursiveComparison()
            .ignoringFields("id", "createdAt", "updatedAt")
            .isEqualTo(mission);
    }

    @DisplayName("name으로 Mission 검색 - 성공")
    @Test
    void findByName() {
        //given
        String name = "[BE] 자동차 미션";
        Mission mission = missionRepository.save(new Mission(name, level));

        //when
        Optional<Mission> foundMission = missionRepository.findByName(name);

        //then
        assertThat(foundMission).isPresent();
        assertThat(foundMission.get()).usingRecursiveComparison()
            .isEqualTo(mission);
    }

    @DisplayName("name으로 Mission 검색 - 실패")
    @Test
    void findByNameFailed() {
        //when
        Optional<Mission> foundMission = missionRepository.findByName("나는 말하는 감자");

        //then
        assertThat(foundMission).isNotPresent();
    }
}
