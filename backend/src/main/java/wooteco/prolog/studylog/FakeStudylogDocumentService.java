package wooteco.prolog.studylog;

import java.time.LocalDate;
import java.util.List;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import wooteco.prolog.studylog.application.AbstractStudylogDocumentService;
import wooteco.prolog.studylog.application.dto.StudylogDocumentPagingDto;
import wooteco.prolog.studylog.domain.repository.StudylogDocumentRepository;
import wooteco.prolog.studylog.domain.repository.StudylogRepository;

@Profile({"local", "test"})
@Service
public class FakeStudylogDocumentService extends AbstractStudylogDocumentService {

    public FakeStudylogDocumentService(
        StudylogDocumentRepository studylogDocumentRepository,
        StudylogRepository studylogRepository) {
        super(studylogDocumentRepository, studylogRepository);
    }

    @Override
    public StudylogDocumentPagingDto findBySearchKeyword(String keyword, List<Long> tags, List<Long> missions,
                                                         List<Long> levels, List<String> usernames, LocalDate start,
                                                         LocalDate end, Pageable pageable) {

        // TODO
        System.out.println(">>>> FakeStudylogDocumentService#findBySearchKeyword");
        return null;
    }
}
