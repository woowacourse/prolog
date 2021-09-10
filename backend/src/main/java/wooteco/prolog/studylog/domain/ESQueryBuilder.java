package wooteco.prolog.studylog.domain;


import java.util.List;

// TODO
// 하지만 QueryBuilder로 Query를 만들어도 @Query안에는 어떻게 넣지?
public class ESQueryBuilder {

    public static final String QUERY = "";

    public static String terms(String key, List<Object> values) {
        return null;
    }
}

/* sample query
{
    "query": {
        "bool": {
        "must": [{
            "query_string": {
                    "fields" : ["title", "content"],
                    "query": "*?0*"
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
                "missionId": ?2
            }
        },
        {
            "terms": {
                "tagIds": ?1
            }
        },
        {
            "terms": {
                "levelId": ?3
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
