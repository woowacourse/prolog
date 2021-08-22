package wooteco.prolog.post.domain.repository;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.mission.domain.Mission;
import wooteco.prolog.post.domain.Post;
import wooteco.prolog.posttag.domain.PostTag;

public interface PostRepository extends JpaRepository<Post, Long> {

    Page<Post> findDistinctByMissionInAndPostTagsValuesIn(List<Mission> missions, List<PostTag> postTags,
                                                          Pageable pageable);

    Page<Post> findDistinctByPostTagsValuesIn(List<PostTag> postTags, Pageable pageable);

    @Query(value = "select distinct p from Post p left join fetch p.postTags.values pt left join fetch pt.tag where p.mission in :missions",
            countQuery = "select count(p) from Post p where p.mission in :missions")
    Page<Post> findByMissionIn(List<Mission> missions, Pageable pageable);

    @Query(value = "select distinct p from Post p left join fetch p.postTags.values pt left join fetch pt.tag where p.member = :member",
            countQuery = "select count(p) from Post p where p.member = :member")
    Page<Post> findByMember(Member member, Pageable pageable);
}