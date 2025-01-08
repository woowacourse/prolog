package wooteco.prolog.studylog.domain.repository;

import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.springframework.data.jpa.domain.Specification;
import wooteco.prolog.studylog.domain.Studylog;

public class StudylogSpecification {

    private StudylogSpecification() {
    }

    public static Specification<Studylog> likeKeyword(String key, List<String> keywords) {
        List<Predicate> predicates = new ArrayList<>();
        return (root, query, builder) -> {
            if (Objects.isNull(keywords) || keywords.isEmpty()) {
                return builder.and();
            }

            for (String keyword : keywords) {
                predicates.add(
                    builder.like(builder.lower(root.get(key).get(key)), "%" + keyword + "%"));
            }

            return builder.or(predicates.toArray(new Predicate[0]));
        };
    }

    public static Specification<Studylog> equalIn(String key, List<Long> values) {
        return (root, query, builder) -> {
            if (values == null || values.isEmpty()) {
                return builder.and();
            }

            return root.get(key).get("id").in(values);
        };
    }

    public static Specification<Studylog> findByTagIn(List<Long> values) {
        return (root, query, builder) -> {
            if (values == null || values.isEmpty() || values.contains(0L)) {
                return builder.and();
            }

            return root.join("studylogTags", JoinType.LEFT).join("values", JoinType.LEFT).get("tag").get("id")
                .in(values);
        };
    }

    public static Specification<Studylog> findByUsernameIn(List<String> usernames) {
        return (root, query, builder) -> {
            if (usernames == null || usernames.isEmpty()) {
                return builder.and();
            }

            return root.join("member", JoinType.LEFT).get("username").in(usernames);
        };
    }

    public static Specification<Studylog> findByDeletedFalse() {
        return (root, query, builder) -> builder.isFalse(root.get("deleted"));
    }

    public static Specification<Studylog> distinct(boolean distinct) {
        return (root, query, builder) -> {
            query.distinct(distinct);
            return null;
        };
    }

    public static Specification<Studylog> findBetweenDate(LocalDate start, LocalDate end) {
        return ((root, query, builder) -> {
            if (start == null && end == null) {
                return builder.and();
            }
            if (start == null) {
                return builder.lessThanOrEqualTo(
                    root.get("createdAt"), end.atTime(LocalTime.MAX)
                );
            }
            if (end == null) {
                return builder.greaterThanOrEqualTo(
                    root.get("createdAt"), start.atStartOfDay()
                );
            }
            return builder.between(
                root.get("createdAt"),
                start.atStartOfDay(),
                end.atTime(LocalTime.MAX)
            );
        });
    }

    public static Specification<Studylog> findByMemberIn(List<Long> members) {
        return (root, query, builder) -> {
            if (members == null || members.isEmpty()) {
                return builder.and();
            }

            return root.join("member", JoinType.LEFT).get("id").in(members);
        };
    }

    public static Specification<Studylog> orderByIdDesc() {
        return (root, query, builder) -> {
            query.orderBy(builder.desc(root.get("id")));
            return null;
        };
    }
}
