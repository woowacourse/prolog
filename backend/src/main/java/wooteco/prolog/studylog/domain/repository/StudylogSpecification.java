package wooteco.prolog.studylog.domain.repository;

import org.springframework.data.jpa.domain.Specification;
import wooteco.prolog.studylog.domain.Studylog;

import javax.persistence.criteria.JoinType;
import java.util.List;

public class StudylogSpecification {
    public static Specification<Studylog> equalIn(String key, List<Long> values) {
        return (root, query, builder) -> {
            if (values == null || values.isEmpty()) {
                return builder.and();
            }

            return root.get(key).in(values);
        };
    }

    public static Specification<Studylog> findByLevelIn(List<Long> values) {
        return (root, query, builder) -> {
            if (values == null || values.isEmpty()) {
                return builder.and();
            }

            return root.join("mission", JoinType.LEFT).join("level", JoinType.LEFT).get("id").in(values);
        };
    }

    public static Specification<Studylog> findByTagIn(List<Long> values) {
        return (root, query, builder) -> {
            if (values == null || values.isEmpty()) {
                return builder.and();
            }

            return root.join("studylogTags", JoinType.LEFT).join("values", JoinType.LEFT).get("tag").in(values);
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

    public static Specification<Studylog> distinct(boolean distinct) {
        return (root, query, builder) -> {
            query.distinct(distinct);
            return null;
        };
    }
}
