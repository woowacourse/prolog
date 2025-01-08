package wooteco.prolog.roadmap.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wooteco.prolog.roadmap.domain.RecommendedPost;

public interface RecommendedPostRepository extends JpaRepository<RecommendedPost, Long> {

}
