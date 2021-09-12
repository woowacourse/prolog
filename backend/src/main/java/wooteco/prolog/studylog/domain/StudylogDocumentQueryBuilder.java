package wooteco.prolog.studylog.domain;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;

public class StudylogDocumentQueryBuilder {

    private static final String WILD_CARD = "*";
    private static final String OR = " OR ";
    private static final String EMPTY = " ";

    public static Query makeQuery(List<String> keywords,
                                  List<Long> tags,
                                  List<Long> missions,
                                  List<Long> levels,
                                  List<String> usernames,
                                  LocalDate start,
                                  LocalDate end,
                                  Pageable pageable
    ) {

        NativeSearchQueryBuilder query = new NativeSearchQueryBuilder();
        makeBoolQuery(query,
                      makeKeywordsQueryString(keywords),
                      makeDefaultQueryString(usernames),
                      makeDefaultQueryString(convertToListString(levels)),
                      makeDefaultQueryString(convertToListString(missions)));
        makeFilterQuery(query, tags);

        return query.withPageable(pageable)
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
        String missions) {
        query.withQuery(QueryBuilders.boolQuery()
                            .must(multiField(keyword))
                            .must(defaultField(username, "username"))
                            .must(defaultField(levels, "levelId"))
                            .must(defaultField(missions, "missionId"))
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

    private static String makeDefaultQueryString(List<String> usernames) {
        if (Objects.isNull(usernames) || usernames.isEmpty()) {
            return WILD_CARD;
        }
        StringBuilder sb = new StringBuilder();
        IntStream.range(0, usernames.size()).forEach(i -> {
            sb.append(usernames.get(i));
            if (i != usernames.size() - 1) {
                sb.append(OR);
            }
        });
        return sb.toString();
    }

    private static String makeKeywordsQueryString(List<String> keywords) {
        if (Objects.isNull(keywords) || keywords.isEmpty()) {
            return WILD_CARD;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < keywords.size(); i++) {
            final String value = keywords.get(i);

            if (value.contains(EMPTY)) {
                sb.append("\"*").append(value).append("*\"");
            } else {
                sb.append(WILD_CARD).append(value).append(WILD_CARD);
            }

            if (i != keywords.size() - 1) {
                sb.append(OR);
            }
        }
        return sb.toString();
    }

    private static void makeFilterQuery(NativeSearchQueryBuilder query, List<Long> tags) {
        if (Objects.isNull(tags) || tags.isEmpty()) {
            return;
        }
        query.withFilter(QueryBuilders.termsQuery("tagIds", tags));
    }

    // TODO 날짜 범위 쿼리
//    private static void makeGTEQuery(NativeSearchQueryBuilder query, LocalDate start) {
//        query.withQuery(QueryBuilders.boolQuery()
//                            .filter(QueryBuilders.rangeQuery("dateTime").gte(start)));
//    }
//
//    private static void makeLTEQuery(NativeSearchQueryBuilder query, LocalDate end) {
//        query.withQuery(QueryBuilders.boolQuery()
//                            .filter(QueryBuilders.rangeQuery("dateTime").lte(end)));
//    }
}
