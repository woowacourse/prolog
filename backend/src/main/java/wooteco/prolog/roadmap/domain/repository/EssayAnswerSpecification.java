package wooteco.prolog.roadmap.domain.repository;

import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;
import wooteco.prolog.roadmap.domain.EssayAnswer;

import java.util.List;

public class EssayAnswerSpecification {

    private EssayAnswerSpecification() {
    }

    public static Specification<EssayAnswer> equalsSessionIdsIn(final List<Long> sessionIds) {
        return (root, query, builder) -> {
            if (sessionIds == null || sessionIds.isEmpty()) {
                return builder.and();
            }

            return root.join("quiz", JoinType.INNER)
                .join("keyword", JoinType.INNER)
                .get("sessionId").in(sessionIds);
        };
    }

    public static Specification<EssayAnswer> equalsKeywordId(Long keywordId) {
        return (root, query, builder) -> {
            if (keywordId == null || keywordId == 0L) {
                return builder.and();
            }

            return root.get("quiz").get("keyword").get("id").in(keywordId);
        };
    }

    public static Specification<EssayAnswer> inQuizIds(List<Long> quizIds) {
        return (root, query, builder) -> {
            if (quizIds == null || quizIds.isEmpty()) {
                return builder.and();
            }

            return root.get("quiz").get("id").in(quizIds);
        };
    }

    public static Specification<EssayAnswer> inMemberIds(List<Long> memberIds) {
        return (root, query, builder) -> {
            if (memberIds == null || memberIds.isEmpty()) {
                return builder.and();
            }

            return root.join("member", JoinType.LEFT).get("id").in(memberIds);
        };
    }

    public static Specification<EssayAnswer> orderByIdDesc() {
        return (root, query, builder) -> {
            query.orderBy(builder.desc(root.get("id")));
            return null;
        };
    }

    public static Specification<EssayAnswer> distinct(final boolean distinct) {
        return (root, query, builder) -> {
            query.distinct(distinct);
            return null;
        };
    }
}
