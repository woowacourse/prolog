package wooteco.prolog.post.application;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wooteco.prolog.level.application.LevelService;
import wooteco.prolog.level.domain.Level;
import wooteco.prolog.member.application.MemberService;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.mission.application.MissionService;
import wooteco.prolog.mission.domain.Mission;
import wooteco.prolog.post.application.dto.PostRequest;
import wooteco.prolog.post.application.dto.PostResponse;
import wooteco.prolog.post.application.dto.PostSpecification;
import wooteco.prolog.post.application.dto.PostsResponse;
import wooteco.prolog.post.domain.Post;
import wooteco.prolog.post.domain.repository.PostRepository;
import wooteco.prolog.post.exception.PostArgumentException;
import wooteco.prolog.post.exception.PostNotFoundException;
import wooteco.prolog.tag.application.TagService;
import wooteco.prolog.tag.domain.Tags;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;
    private final MissionService missionService;
    private final MemberService memberService;
    private final TagService tagService;
    private LevelService levelService;

    public PostsResponse findPostsWithFilter(
            List<Long> levelIds,
            List<Long> missionIds,
            List<Long> tagIds,
            Pageable pageable) {

        Specification<Post> specs = PostSpecification.equalIn("level", levelIds)
                .and(PostSpecification.equalIn("mission", missionIds))
                .and(PostSpecification.equalTagIn(tagIds))
                .and(PostSpecification.distinct(true));

        Page<Post> posts = postRepository.findAll(specs, pageable);

        return PostsResponse.of(posts);
    }

    public PostsResponse findPostsOf(String username, Pageable pageable) {
        Member member = memberService.findByUsername(username);
        return PostsResponse.of(postRepository.findByMember(member, pageable));
    }

    @Transactional
    public List<PostResponse> insertPosts(Member member, List<PostRequest> postRequests) {
        if (postRequests.size() == 0) {
            throw new PostArgumentException();
        }

        return postRequests.stream()
                .map(postRequest -> insertPost(member, postRequest))
                .collect(Collectors.toList());
    }

    private PostResponse insertPost(Member member, PostRequest postRequest) {
        Tags tags = tagService.findOrCreate(postRequest.getTags());
        Mission mission = missionService.findById(postRequest.getMissionId());
        Level level = levelService.findById(postRequest.getLevelId());

        Post requestedPost = new Post(member,
                postRequest.getTitle(),
                postRequest.getContent(),
                level,
                mission,
                tags.getList());

        Post createdPost = postRepository.save(requestedPost);

        return PostResponse.of(createdPost);
    }

    public PostResponse findById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(PostNotFoundException::new);

        return PostResponse.of(post);
    }

    @Transactional
    public void updatePost(Member member, Long postId, PostRequest postRequest) {
        Post post = postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);
        post.validateAuthor(member);

        Level level = levelService.findById(postRequest.getLevelId());
        Mission mission = missionService.findById(postRequest.getMissionId());
        Tags tags = tagService.findOrCreate(postRequest.getTags());
        post.update(postRequest.getTitle(), postRequest.getContent(), level, mission, tags);
    }

    @Transactional
    public void deletePost(Member member, Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);
        post.validateAuthor(member);

        postRepository.delete(post);
    }
}
