package wooteco.prolog.roadmap.application;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wooteco.prolog.roadmap.application.dto.CurriculumRequest;
import wooteco.prolog.roadmap.application.dto.CurriculumResponses;
import wooteco.prolog.roadmap.domain.Curriculum;
import wooteco.prolog.roadmap.domain.repository.CurriculumRepository;

@Transactional
@Service
public class CurriculumService {

    private final CurriculumRepository curriculumRepository;

    public CurriculumService(CurriculumRepository repository) {
        this.curriculumRepository = repository;
    }

    public Long create(final CurriculumRequest request) {
        final Curriculum savedCurriculum = curriculumRepository.save(request.toEntity());
        return savedCurriculum.getId();
    }

    public CurriculumResponses findCurriculums() {
        final List<Curriculum> curriculums = curriculumRepository.findAll();
        return CurriculumResponses.createResponse(curriculums);
    }

}
