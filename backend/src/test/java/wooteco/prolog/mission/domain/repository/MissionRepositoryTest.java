package wooteco.prolog.mission.domain.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import wooteco.prolog.mission.domain.Mission;

@DataJpaTest
class MissionRepositoryTest {

    @Autowired
    MissionRepository missionRepository;

    @DisplayName("Mission 생성")
    @Test
    void createMission() {
        // given
        Mission mission = new Mission("[BE] 자동차 미션");

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
        Mission mission = 미션을_생성한다(name);

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

    private Mission 미션을_생성한다(String name) {
        return missionRepository.save(new Mission(name));
    }
}