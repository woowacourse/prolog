package wooteco.prolog.studylog.domain.repository;

import org.springframework.data.jpa.domain.Specification;
import wooteco.prolog.studylog.domain.Post;

import javax.persistence.criteria.JoinType;
import java.util.List;

public class PostSpecification {
    public static Specification<Post> equalIn(String key, List<Long> values) {
        return (root, query, builder) -> {
            if (values == null || values.isEmpty()) {
                return builder.and();
            }

            return root.get(key).in(values);
        };
    }

    public static Specification<Post> equalIn(String key, List<Long> values, boolean isSearch) {
        return (root, query, builder) -> {
            if (!isSearch && (values == null || values.isEmpty())) {
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
            if (values == null || values.isEmpty()) {
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

    public static Specification<Post> distinct(boolean distinct) {
        return (root, query, builder) -> {
            query.distinct(distinct);
            return null;
        };
    }
}
