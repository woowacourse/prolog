package wooteco.prolog.studylog.domain;

import static wooteco.prolog.studylog.domain.DocumentQueryParser.makeDefaultQueryString;
import static wooteco.prolog.studylog.domain.DocumentQueryParser.makeKeywordsQueryString;
import static wooteco.prolog.studylog.domain.DocumentQueryParser.removeSpecialChars;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;

public class StudylogDocumentQueryBuilder {

    private StudylogDocumentQueryBuilder() {
    }

    public static Query makeQuery(List<String> inputKeywords,
                                  List<Long> tags,
                                  List<Long> missions,
                                  List<Long> levels,
                                  List<String> usernames,
                                  LocalDate start,
                                  LocalDate end,
                                  Pageable pageable
    ) {

        List<String> keywords = removeSpecialChars(inputKeywords);
        NativeSearchQueryBuilder query = new NativeSearchQueryBuilder();
        makeBoolQuery(query,
                      makeKeywordsQueryString(keywords),
                      makeDefaultQueryString(usernames),
                      makeDefaultQueryString(convertToListString(levels)),
                      makeDefaultQueryString(convertToListString(missions)),
                      start,
                      end);
        makeFilterQuery(query, tags);

        return query
            .withPageable(pageable)
            .build();
    }

    private static List<String> convertToListString(List<Long> values) {
        return values.stream()
            .map(String::valueOf)
            .collect(Collectors.toList());
    }

    private static void makeBoolQuery(
        NativeSearchQueryBuilder query,
        String keyword,
        String username,
        String levels,
        String missions,
        LocalDate start,
        LocalDate end) {
        query.withQuery(QueryBuilders.boolQuery()
                            .must(multiField(keyword))
                            .must(defaultField(username, "username"))
                            .must(defaultField(levels, "levelId"))
                            .must(defaultField(missions, "missionId"))
                            .filter(rangeQuery(start, end))
        );
    }

    private static QueryStringQueryBuilder multiField(String keyword) {
        return QueryBuilders.queryStringQuery(keyword)
            .field("title")
            .field("content");
    }

    private static QueryStringQueryBuilder defaultField(String query, String field) {
        return QueryBuilders
            .queryStringQuery(query)
            .defaultField(field);
    }

    private static void makeFilterQuery(NativeSearchQueryBuilder query, List<Long> tags) {
        if (Objects.isNull(tags) || tags.isEmpty()) {
            return;
        }
        query.withFilter(QueryBuilders.termsQuery("tagIds", tags));
    }

    private static RangeQueryBuilder rangeQuery(LocalDate start, LocalDate end) {
        if (Objects.isNull(start)) {
            start = LocalDate.parse("19000101", DateTimeFormatter.BASIC_ISO_DATE);
        }
        if (Objects.isNull(end)) {
            end = LocalDate.parse("99991231", DateTimeFormatter.BASIC_ISO_DATE);
        }

        return QueryBuilders.rangeQuery("dateTime")
            .from(start)
            .to(end);
    }
}
