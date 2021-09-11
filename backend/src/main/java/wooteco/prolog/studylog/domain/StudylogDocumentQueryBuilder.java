package wooteco.prolog.studylog.domain;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;

public class StudylogDocumentQueryBuilder {

    public static Query makeQuery(String keyword,
                                  List<Long> tags,
                                  List<Long> missions,
                                  List<Long> levels,
                                  List<String> usernames,
                                  LocalDate start,
                                  LocalDate end,
                                  Pageable pageable
    ) {

        NativeSearchQueryBuilder query = new NativeSearchQueryBuilder();
        if (Objects.nonNull(keyword) && !keyword.isEmpty()) {
            makeTitleAndContentQuery(query, keyword);
        }

        if (Objects.nonNull(usernames) && !usernames.isEmpty()) {
            makeUserNameQuery(query, usernames);
        }

        if (Objects.nonNull(start)) {
            makeGTEQuery(query, start);
        }

        if (Objects.nonNull(end)) {
            makeLTEQuery(query, end);
        }

        if (Objects.nonNull(tags) && !tags.isEmpty()) {
            makeTermsQuery(query, "tagIds", tags);
        }

        if (Objects.nonNull(missions) && !missions.isEmpty()) {
            makeTermsQuery(query, "missionId", missions);
        }

        if (Objects.nonNull(levels) && !levels.isEmpty()) {
            makeTermsQuery(query, "levelId", levels);
        }

        return query.withPageable(pageable)
            .build();
    }

    private static void makeTitleAndContentQuery(
        NativeSearchQueryBuilder query,
        String keyword) {
        query.withQuery(QueryBuilders.boolQuery()
                            .must(QueryBuilders.queryStringQuery("*" + keyword + "*")
                                      .field("title")
                                      .field("content"))
        );
    }

    private static void makeUserNameQuery(NativeSearchQueryBuilder query, List<String> usernames) {
        query.withQuery(QueryBuilders.boolQuery()
                            .must(QueryBuilders
                                      .queryStringQuery(makeUserNameQueryString(usernames))
                                      .defaultField("username")));
    }

    private static void makeGTEQuery(NativeSearchQueryBuilder query, LocalDate start) {
        query.withQuery(QueryBuilders.boolQuery()
                            .filter(QueryBuilders.rangeQuery("dateTime").gte(start)));
    }

    private static void makeLTEQuery(NativeSearchQueryBuilder query, LocalDate end) {
        query.withQuery(QueryBuilders.boolQuery()
                            .filter(QueryBuilders.rangeQuery("dateTime").lte(end)));
    }

    private static void makeTermsQuery(NativeSearchQueryBuilder query, String field,
                                       List<Long> list) {
        query.withFilter(QueryBuilders.termsQuery(field, list));
    }

    private static String makeUserNameQueryString(List<String> usernames) {
        final String OR = " OR ";
        StringBuilder sb = new StringBuilder();
        IntStream.range(0, usernames.size()).forEach(i -> {
            sb.append(usernames.get(i));
            if (i != usernames.size() - 1) {
                sb.append(OR);
            }
        });
        return sb.toString();
    }
}
