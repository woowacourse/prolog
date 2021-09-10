package wooteco.prolog.studylog.domain.repository;

import static wooteco.prolog.studylog.domain.ESQueryBuilder.QUERY;

import java.util.List;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import wooteco.prolog.studylog.domain.ESQueryBuilder;
import wooteco.prolog.studylog.domain.StudylogDocument;

@Profile({"dev", "prod"})
public interface StudylogDocumentRepository extends
    ElasticsearchRepository<StudylogDocument, Long> {

    @Query("{\"query_string\": {\"fields\": [\"title\",\"content\"], \"query\": \"*?0*\"}}")
    Page<StudylogDocument> findByKeyword(String keyword, Pageable pageable);

    @Query(value = QUERY)
    Page<StudylogDocument> findByMultipleConditions(
        String keyword,
        List<Long> tags,
        List<Long> missions,
        List<Long> levels,
        List<String> usernames,
        Pageable pageable
    );
}

/* sample query
{
    "query": {
        "bool": {
        "must": [{
            "query_string": {
                    "fields" : ["title", "content"],
                    "query": "*페이지*"
            }
            },
            {
                "query_string": {
                    "default_field": "username",
                    "query": "gracefulBrown OR seovalue"
                }
            }
        ],
        "filter": [
        {
            "terms": {
                "missionId": [1,2,3,4]
            }
        },
        {
            "terms": {
                "tagIds": [1,2,3,4]
            }
        },
        {
            "range": {
                "dateTime": {
                    "gt": "31/12/2015",
                    "lt": "2022",
                    "format": "dd/MM/yyyy||yyyy||epoch_millis"
                }
            }
        }
        ]
        }
    }
}
 */
