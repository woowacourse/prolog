package wooteco.prolog.article.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import wooteco.prolog.article.domain.Article;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    List<Article> findAllByOrderByCreatedAtDesc();

    @Query("SELECT a FROM Article a ORDER BY COALESCE(a.publishedAt, a.createdAt) desc")
    List<Article> findAllOrderByPublishedAtOrCreatedAt();

    @Query("select a from Article a join fetch a.articleBookmarks where a.id = :id")
    Optional<Article> findFetchBookmarkById(@Param("id") final Long id);

    @Query("select a from Article a join fetch a.articleLikes where a.id = :id")
    Optional<Article> findFetchLikeById(@Param("id") final Long id);

    @Query("SELECT DISTINCT a FROM Article a " +
        "JOIN DepartmentMember dm ON a.member.id = dm.member.id " +
        "JOIN dm.department d " +
        "WHERE d.part = :course " +
        "ORDER by a.createdAt desc")
    List<Article> findArticlesByCourse(@Param("course") String course);

    @Query("SELECT DISTINCT a FROM Article a " +
        "JOIN DepartmentMember dm ON a.member.id = dm.member.id " +
        "JOIN dm.department d " +
        "LEFT JOIN a.articleBookmarks.articleBookmarks ab " +
        "LEFT JOIN a.articleLikes.articleLikes al " +
        "WHERE d.part = :course AND (:onlyBookmarked = false OR (:onlyBookmarked = true and ab.memberId = :memberId))" +
        "ORDER by a.createdAt desc")
    List<Article> findArticlesByCourseAndMember(@Param("course") String course,
                                                @Param("memberId") Long memberId,
                                                @Param("onlyBookmarked") boolean onlyBookmarked);

    List<Article> findAllByMemberId(Long memberId);
}
