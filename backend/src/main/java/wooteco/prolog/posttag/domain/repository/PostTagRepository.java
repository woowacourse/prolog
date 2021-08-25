package wooteco.prolog.posttag.domain.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import wooteco.prolog.posttag.domain.PostTag;
import wooteco.prolog.tag.domain.Tag;

public interface PostTagRepository extends JpaRepository<PostTag, Long> {

    List<PostTag> findByTagIn(List<Tag> tags);

    @Query("select pt from PostTag pt join fetch pt.tag where pt.post.member.id = :memberId")
    List<PostTag> findByMember(Long memberId);
}
