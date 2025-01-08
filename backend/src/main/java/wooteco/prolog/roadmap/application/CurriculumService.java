package wooteco.prolog.roadmap.application;

import static wooteco.prolog.common.exception.BadRequestCode.CURRICULUM_NOT_FOUND_EXCEPTION;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wooteco.prolog.common.exception.BadRequestException;
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

    @Transactional(readOnly = true)
    public CurriculumResponses findCurriculums() {
        final List<Curriculum> curriculums = curriculumRepository.findAll();
        return CurriculumResponses.createResponse(curriculums);
    }


    public void update(Long curriculumId, CurriculumRequest createRequest) {
        final Curriculum curriculum = getCurriculum(curriculumId);

        curriculum.updateName(createRequest.getName());
    }

    public void delete(Long curriculumId) {
        final Curriculum curriculum = getCurriculum(curriculumId);
        curriculumRepository.delete(curriculum);
    }

    private Curriculum getCurriculum(Long curriculumId) {
        return curriculumRepository.findById(curriculumId)
            .orElseThrow(() -> new BadRequestException(CURRICULUM_NOT_FOUND_EXCEPTION));
    }
}
