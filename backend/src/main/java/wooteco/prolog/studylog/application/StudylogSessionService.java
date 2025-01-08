package wooteco.prolog.studylog.application;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wooteco.prolog.studylog.domain.Studylog;
import wooteco.prolog.studylog.domain.repository.StudylogRepository;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class StudylogSessionService {

    private final StudylogRepository studylogRepository;

    public void syncStudylogSession() {
        List<Studylog> studylogs = studylogRepository.findAll();

        studylogs.stream()
            .forEach(it -> it.updateSession(it.getMission().getSession()));
    }
}
