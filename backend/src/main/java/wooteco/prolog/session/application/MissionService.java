package wooteco.prolog.session.application;

import static java.util.stream.Collectors.toList;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wooteco.prolog.login.ui.LoginMember;
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
        return MissionResponse.listOf(missionRepository.findAll());
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

    public List<MissionResponse> findMyMissions(LoginMember loginMember) {
        List<Long> mySessionIds = sessionService.findMySessionIds(loginMember.getId());
        List<Mission> missions = missionRepository.findBySessionIdIn(mySessionIds);
        return MissionResponse.listOf(missions);
    }

    public List<MissionResponse> findAllWithMyMissionFirst(LoginMember loginMember) {
        if (loginMember.isAnonymous()) {
            return findAll();
        }

        List<MissionResponse> myMissions = findMyMissions(loginMember);
        List<MissionResponse> allMissions = findAll();
        allMissions.removeAll(myMissions);

        return Stream.of(myMissions, allMissions)
            .flatMap(Collection::stream)
            .collect(toList());
    }
}
