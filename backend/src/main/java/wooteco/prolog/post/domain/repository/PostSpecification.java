package wooteco.prolog.post.domain.repository;

import static java.time.temporal.TemporalAdjusters.firstDayOfMonth;
import static java.time.temporal.TemporalAdjusters.lastDayOfMonth;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import javax.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;
import wooteco.prolog.post.domain.Post;

public class PostSpecification {
    public static Specification<Post> equalIn(String key, List<Long> values) {
        return (root, query, builder) -> {
            if (values == null || values.isEmpty()) {
                return builder.and();
            }

            return root.get(key).in(values);
        };
    }

    public static Specification<Post> findByLevelIn(List<Long> values) {
        return (root, query, builder) -> {
            if (values == null || values.isEmpty()) {
                return builder.and();
            }

            return root.join("mission", JoinType.LEFT).join("level", JoinType.LEFT).get("id").in(values);
        };
    }

    public static Specification<Post> findByTagIn(List<Long> values) {
        return (root, query, builder) -> {
            if (values == null || values.isEmpty() || values.contains(0L)) {
                return builder.and();
            }

            return root.join("postTags", JoinType.LEFT).join("values", JoinType.LEFT).get("tag").in(values);
        };
    }

    public static Specification<Post> findByUsernameIn(List<String> usernames) {
        return (root, query, builder) -> {
            if (usernames == null || usernames.isEmpty()) {
                return builder.and();
            }

            return root.join("member", JoinType.LEFT).get("username").in(usernames);
        };
    }

    public static Specification<Post> findBetweenDate(LocalDate start, LocalDate end) {
        return ((root, query, builder) -> {
            if (start == null && end == null) {
                return builder.and();
            }
            if (start == null) {
                return builder.lessThanOrEqualTo(
                    root.get("createdAt"), end.with(lastDayOfMonth()).atTime(LocalTime.MAX)
                );
            }
            if (end == null) {
                return builder.greaterThanOrEqualTo(
                    root.get("createdAt"), start.with(firstDayOfMonth()).atStartOfDay()
                );
            }
            return builder.between(
                root.get("createdAt"),
                start.with(firstDayOfMonth()).atStartOfDay(),
                end.with(lastDayOfMonth()).atTime(LocalTime.MAX)
            );
        });
    }

    public static Specification<Post> distinct(boolean distinct) {
        return (root, query, builder) -> {
            query.distinct(distinct);
            return null;
        };
    }
}
