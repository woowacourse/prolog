package wooteco.prolog.studylog.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import wooteco.prolog.studylog.domain.PostTag;
import wooteco.prolog.studylog.domain.Tag;

import java.util.List;

public interface PostTagRepository extends JpaRepository<PostTag, Long> {

    List<PostTag> findByTagIn(List<Tag> tags);

    @Query("select pt.tag from PostTag pt inner join pt.tag group by pt.tag")
    List<Tag> findTagsIncludedInPost();
}
