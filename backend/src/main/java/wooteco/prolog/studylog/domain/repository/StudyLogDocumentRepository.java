package wooteco.prolog.studylog.domain.repository;

import org.springframework.context.annotation.Profile;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import wooteco.prolog.studylog.domain.StudyLogDocument;

@Profile({"elastic", "dev", "prod"})
public interface StudyLogDocumentRepository extends
    ElasticsearchRepository<StudyLogDocument, Long> {

}
