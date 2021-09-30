package wooteco.prolog.report.domain.repository;

import org.springframework.context.annotation.Profile;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import wooteco.prolog.studylog.domain.StudylogDocument;

@Profile({"dev", "prod"})
public interface StudylogDocumentRepository extends
    ElasticsearchRepository<StudylogDocument, Long> {

}
