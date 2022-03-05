package wooteco.prolog.studylog.application;

import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wooteco.prolog.studylog.domain.StudylogMission;
import wooteco.prolog.studylog.domain.repository.StudylogMissionRepository;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class StudylogMissionService {

    private StudylogMissionRepository studylogMissionRepository;

    public void save(Long studylogId, Long missionId) {
        Optional<StudylogMission> studylogMission = studylogMissionRepository.findByStudylogId(studylogId);
        if (studylogMission.isPresent()) {
            studylogMission.get().updateMissionId(missionId);
            return;
        }
        studylogMissionRepository.save(new StudylogMission(studylogId, missionId));
    }
}
