package wooteco.prolog.studylog.application;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wooteco.prolog.studylog.application.dto.MissionRequest;
import wooteco.prolog.studylog.application.dto.MissionResponse;
import wooteco.prolog.studylog.domain.Level;
import wooteco.prolog.studylog.domain.Mission;
import wooteco.prolog.studylog.domain.repository.MissionRepository;
import wooteco.prolog.studylog.exception.DuplicateMissionException;
import wooteco.prolog.studylog.exception.MissionNotFoundException;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class MissionService {

    private final LevelService levelService;
    private final MissionRepository missionRepository;

    @Transactional
    public MissionResponse create(MissionRequest missionRequest) {
        validateName(missionRequest.getName());

        Level level = levelService.findById(missionRequest.getLevelId());
        return MissionResponse.of(missionRepository.save(new Mission(missionRequest.getName(), level)));
    }

    private void validateName(String name) {
        if (missionRepository.findByName(name).isPresent()) {
            throw new DuplicateMissionException();
        }
    }

    public List<MissionResponse> findAll() {
        return missionRepository.findAll()
                .stream()
                .map(MissionResponse::of)
                .collect(toList());
    }

    public Mission findById(Long id) {
        return missionRepository.findById(id)
                .orElseThrow(MissionNotFoundException::new);
    }

    public List<Mission> findByIds(List<Long> missionIds) {
        return missionRepository.findAllById(missionIds);
    }
}
