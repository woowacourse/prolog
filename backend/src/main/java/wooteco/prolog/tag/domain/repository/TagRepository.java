package wooteco.prolog.tag.domain.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import wooteco.prolog.tag.domain.Tag;

public interface TagRepository extends JpaRepository<Tag, Long> {

    List<Tag> findByNameValueIn(List<String> name);
}
