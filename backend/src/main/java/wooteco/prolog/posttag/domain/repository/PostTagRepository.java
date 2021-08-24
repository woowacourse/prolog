package wooteco.prolog.posttag.domain.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import wooteco.prolog.posttag.domain.PostTag;
import wooteco.prolog.tag.domain.Tag;

public interface PostTagRepository extends JpaRepository<PostTag, Long> {

    List<PostTag> findByTagIn(List<Tag> tags);
}
