package wooteco.prolog.post.application.dto;

import org.springframework.data.jpa.domain.Specification;
import wooteco.prolog.post.domain.Post;

import javax.persistence.criteria.JoinType;
import java.util.List;

public class PostSpecification {
    public static Specification<Post> equalIn(String key, List<Long> values) {
        return (root, query, builder) -> {
            if (!values.isEmpty()) {
                return root.get(key).in(values);
            } else {
                return builder.and();
            }
        };
    }

    public static Specification<Post> equalTagIn(List<Long> values) {
        return (root, query, builder) -> {
            if (!values.isEmpty()) {
                return root.join("postTags", JoinType.LEFT).join("values", JoinType.LEFT).get("tag").in(values);
            } else {
                return builder.and();
            }
        };
    }

    public static Specification<Post> distinct(boolean distinct) {
        return (root, query, builder) -> {
            query.distinct(distinct);
            return null;
        };
    }
}
