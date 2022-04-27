package wooteco.prolog.studylog.mission.domain.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import wooteco.prolog.session.domain.Session;
import wooteco.prolog.session.domain.Mission;
import wooteco.prolog.session.domain.repository.SessionRepository;
import wooteco.prolog.session.domain.repository.MissionRepository;
import wooteco.support.utils.RepositoryTest;

@RepositoryTest
class MissionRepositoryTest {

    @Autowired
    SessionRepository sessionRepository;

    @Autowired
    MissionRepository missionRepository;

    private Session session;

    @BeforeEach
    void setUp() {
        session = sessionRepository.save(new Session("세션1"));
    }

    @DisplayName("Mission 생성")
    @Test
    void createMission() {
        // given
        Mission mission = new Mission("[BE] 자동차 미션", session);

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
        Mission mission = missionRepository.save(new Mission(name, session));

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
