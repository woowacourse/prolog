package wooteco.prolog.article.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wooteco.prolog.article.domain.Article;

public interface ArticleRepository extends JpaRepository<Article, Long> {

}
