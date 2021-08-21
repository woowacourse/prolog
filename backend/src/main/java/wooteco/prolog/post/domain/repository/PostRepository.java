package wooteco.prolog.post.domain.repository;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.mission.domain.Mission;
import wooteco.prolog.post.domain.Post;
import wooteco.prolog.posttag.domain.PostTag;

public interface PostRepository extends JpaRepository<Post, Long> {

    Page<Post> findByMissionInAndPostTagsIn(List<Mission> missions, List<PostTag> postTags, Pageable pageable);

    Page<Post> findByPostTagsIn(List<PostTag> postTags, Pageable pageable);

    Page<Post> findByMissionIn(List<Mission> missions, Pageable pageable);

    Page<Post> findByMember(Member member, Pageable pageable);
}