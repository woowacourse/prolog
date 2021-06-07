package wooteco.prolog.mission.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wooteco.prolog.mission.application.dto.MissionRequest;
import wooteco.prolog.mission.application.dto.MissionResponse;
import wooteco.prolog.mission.dao.MissionDao;
import wooteco.prolog.mission.domain.Mission;
import wooteco.prolog.mission.exception.MissionNotFoundException;
import wooteco.prolog.mission.exception.DuplicateMissionException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MissionService {

    private final MissionDao missionDao;

    public MissionService(MissionDao missionDao) {
        this.missionDao = missionDao;
    }

    @Transactional
    public MissionResponse create(MissionRequest missionRequest) {
        validateName(missionRequest.getName());

        Mission mission = new Mission(missionRequest.getName());
        return MissionResponse.of(missionDao.insert(mission));
    }

    private void validateName(String name) {
        if (missionDao.findByName(name).isPresent()) {
            throw new DuplicateMissionException();
        }
    }

    public List<MissionResponse> findAll() {
        return missionDao.findAll()
                .stream()
                .map(MissionResponse::of)
                .collect(Collectors.toList());
    }

    public MissionResponse findById(Long id) {
        Mission mission = missionDao.findById(id)
                .orElseThrow(MissionNotFoundException::new);
        return MissionResponse.of(mission);
    }
}
