package wooteco.prolog.studylog.application;

import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import wooteco.prolog.studylog.application.dto.StudylogDocumentResponse;
import wooteco.prolog.studylog.domain.repository.StudylogRepository;

import java.time.LocalDate;
import java.util.List;

@Profile({"dev", "prod"})
@Service
public class StudylogDocumentService extends AbstractStudylogDocumentService {


    public StudylogDocumentService(
        StudylogRepository studylogRepository) {
        super(studylogRepository);
    }

    @Override
    public StudylogDocumentResponse findBySearchKeyword(
        String keyword,
        List<Long> tags,
        List<Long> missions,
        List<Long> levels,
        List<String> usernames,
        LocalDate start,
        LocalDate end,
        Pageable pageable
    ) {
        return new StudylogDocumentResponse();
    }
}
