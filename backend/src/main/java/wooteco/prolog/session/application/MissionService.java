package wooteco.prolog.session.application;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wooteco.prolog.session.application.dto.MissionRequest;
import wooteco.prolog.session.application.dto.MissionResponse;
import wooteco.prolog.session.domain.Mission;
import wooteco.prolog.session.domain.Session;
import wooteco.prolog.session.domain.repository.MissionRepository;
import wooteco.prolog.studylog.exception.DuplicateMissionException;
import wooteco.prolog.studylog.exception.MissionNotFoundException;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class MissionService {

    private final SessionService sessionService;
    private final MissionRepository missionRepository;

    @Transactional
    public MissionResponse create(MissionRequest missionRequest) {
        validateName(missionRequest.getName());

        Session session = sessionService.findById(missionRequest.getSessionId());
        return MissionResponse.of(missionRepository.save(new Mission(missionRequest.getName(), session)));
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

    public Optional<Mission> findMissionById(Long id) {
        if (id == null) {
            return Optional.empty();
        }
        return missionRepository.findById(id);
    }

    public List<Mission> findByIds(List<Long> missionIds) {
        return missionRepository.findAllById(missionIds);
    }
}
