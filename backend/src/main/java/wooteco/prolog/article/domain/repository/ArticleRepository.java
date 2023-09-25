package wooteco.prolog.article.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import wooteco.prolog.article.domain.Article;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    List<Article> findAllByOrderByCreatedAtDesc();

    @Query("select a from Article a join fetch a.articleBookmarks where a.id = :id")

 
    Optional<Article> findFetchBookmarkById(@Param("id") final Long id);

    @Query("select a from Article a join fetch a.articleLikes where a.id = :id")
    Optional<Article> findFetchLikeById(@Param("id") final Long id);
  
    Optional<Article> findFetchById(@Param("id") final Long id);

    @Query("SELECT DISTINCT a FROM Article a " +
        "JOIN GroupMember gm ON a.member.id = gm.member.id " +
        "JOIN gm.group mg " +
        "WHERE mg.name LIKE %:course")
    List<Article> findArticlesByCourse(@Param("course") String course);

    @Query("SELECT DISTINCT a FROM Article a " +
        "JOIN GroupMember gm ON a.member.id = gm.member.id " +
        "JOIN gm.group mg " +
        "JOIN a.articleBookmarks.articleBookmarks ab " +
        "WHERE mg.name LIKE %:course AND ab.memberId = :memberId")
 }
