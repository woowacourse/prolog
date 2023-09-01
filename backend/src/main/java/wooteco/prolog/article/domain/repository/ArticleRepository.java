package wooteco.prolog.article.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wooteco.prolog.article.domain.Article;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    List<Article> findAllByOrderByCreatedAtDesc();
}
