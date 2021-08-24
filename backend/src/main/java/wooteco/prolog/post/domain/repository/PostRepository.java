package wooteco.prolog.post.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.post.domain.Post;

public interface PostRepository extends JpaRepository<Post, Long>, JpaSpecificationExecutor<Post> {
    @Query(value = "select distinct p from Post p left join fetch p.postTags.values pt left join fetch pt.tag where p.member = :member",
            countQuery = "select count(p) from Post p where p.member = :member")
    Page<Post> findByMember(Member member, Pageable pageable);
}