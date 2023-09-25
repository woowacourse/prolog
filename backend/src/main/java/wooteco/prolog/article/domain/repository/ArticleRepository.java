package wooteco.prolog.article.domain.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import wooteco.prolog.article.domain.Article;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    List<Article> findAllByOrderByCreatedAtDesc();

    @Query("select a from Article a join fetch a.articleBookmarks where a.id = :id")
    Optional<Article> findFetchBookmarkById(@Param("id") final Long id);

    @Query("select a from Article a join fetch a.articleLikes where a.id = :id")
    Optional<Article> findFetchLikeById(@Param("id") final Long id);
}
