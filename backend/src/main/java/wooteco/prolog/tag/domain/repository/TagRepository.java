package wooteco.prolog.tag.domain.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.post.domain.Post;
import wooteco.prolog.tag.domain.Tag;

public interface TagRepository extends JpaRepository<Tag, Long> {

    List<Tag> findByNameValueIn(List<String> name);

    @Query("select t from Tag t where t.id in (select pt.tag.id from PostTag pt where pt.post = :post and pt.post.member = :member)")
    List<Tag> findByPostAndMember(Post post, Member member);
}
