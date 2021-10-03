package wooteco.prolog.studylog.domain.repository;

import static wooteco.prolog.member.domain.QMember.member;
import static wooteco.prolog.studylog.domain.QLevel.level;
import static wooteco.prolog.studylog.domain.QMission.mission;
import static wooteco.prolog.studylog.domain.QStudylog.studylog;
import static wooteco.prolog.studylog.domain.QStudylogTag.studylogTag;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import javax.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import wooteco.prolog.studylog.domain.Studylog;

@Repository
public class StudylogRepositoryImpl implements
    StudylogRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    public StudylogRepositoryImpl(EntityManager em) {
        this.jpaQueryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<Studylog> search(StudylogSearchCondition condition) {
        JPAQuery<Studylog> query = createQuery(condition);
        return query.fetch();
    }

    @Override
    public Page<Studylog> search(StudylogSearchCondition condition, Pageable pageable) {
        JPAQuery<Studylog> query = createQuery(condition);
        sortAndPaging(query, pageable);
        QueryResults<Studylog> studylogQueryResults = query
            .fetchResults();

        List<Studylog> results = studylogQueryResults.getResults();
        long total = studylogQueryResults.getTotal();
        return new PageImpl<>(results, pageable, total);
    }

    private JPAQuery<Studylog> createQuery(StudylogSearchCondition condition
    ) {
        JPAQuery<Studylog> query = jpaQueryFactory.selectFrom(studylog);
        joinTable(query, condition);
        whereExpression(query, condition);
        return query.distinct();
    }

    private void joinTable(JPAQuery<Studylog> query,
                           StudylogSearchCondition condition) {

        if (isExist(condition.getLevels())) {
            query.leftJoin(studylog.mission.level, level);
        }

        if (isExist(condition.getMissions())) {
            query.leftJoin(studylog.mission, mission);
        }

        if (isExist(condition.getUsernames()) || isExist(condition.getMembers())) {
            query.leftJoin(studylog.member, member);
        }

        if (isExist(condition.getTags())) {
            query.leftJoin(studylog.studylogTags.values, studylogTag);
        }

        if (!condition.hasOnlySearch()) {
            query.fetchJoin();
        }
    }

    private void sortAndPaging(JPAQuery<Studylog> query, Pageable pageable) {
        query.offset(pageable.getOffset())
            .limit(pageable.getPageSize());

        for (Sort.Order o : pageable.getSort()) {
            PathBuilder<Studylog> pathBuilder = new PathBuilder<>(studylog.getType(),
                studylog.getMetadata());

            OrderSpecifier orderSpecifier = new OrderSpecifier(
                o.isAscending() ? Order.ASC : Order.DESC,
                pathBuilder.get(o.getProperty())
            );

            query.orderBy(orderSpecifier);
        }
    }

    private void whereExpression(JPAQuery<Studylog> query, StudylogSearchCondition condition) {
        query.where(
            likeTitle(condition.getKeywords()),
            createExpressionForList(studylog.mission.level.id::in, condition.getLevels()),
            createExpressionForList(studylog.mission.id::in, condition.getMissions()),
            createExpressionForList(studylogTag.tag.id::in, condition.getTags()),
            createExpressionForList(studylog.member.username::in, condition.getUsernames()),
            graterOrEqualThan(condition.getStartDate()),
            lesserOrEqualThan(condition.getEndDate())
        );
    }

    private BooleanExpression likeTitle(List<String> titleKeywords) {
        if (isExist(titleKeywords)) {
            BooleanExpression contains = studylog.title.title.contains(titleKeywords.get(0));
            contains.or(studylog.content.content.contains(titleKeywords.get(0)));
            for (int i = 1; i < titleKeywords.size(); i++) {
                contains.or(studylog.title.title.contains(titleKeywords.get(i)));
                contains.or(studylog.content.content.contains(titleKeywords.get(i)));
            }
            return contains;
        }
        return null;
    }

    private boolean isExist(List<?> ids) {
        return !Objects.isNull(ids) && !ids.isEmpty();
    }

    private BooleanExpression graterOrEqualThan(LocalDate localDate) {
        return Objects.isNull(localDate) ? null : studylog.createdAt.goe(localDate.atStartOfDay());
    }

    private BooleanExpression lesserOrEqualThan(LocalDate localDate) {
        return Objects.isNull(localDate) ? null : studylog.createdAt.loe(LocalDateTime.of(localDate, LocalTime.MAX).withNano(0));
    }

    private <T> BooleanExpression createExpressionForList(
        WhereExpressionForList<T> expression,
        List<T> parameters) {
        return isExist(parameters) ? expression.setParameter(parameters) : null;
    }

    private interface WhereExpressionForList<T> {

        BooleanExpression setParameter(List<T> list);
    }

}
