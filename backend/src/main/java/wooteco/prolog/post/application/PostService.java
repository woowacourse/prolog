package wooteco.prolog.post.application;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wooteco.prolog.member.application.MemberService;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.mission.application.MissionService;
import wooteco.prolog.mission.domain.Mission;
import wooteco.prolog.post.application.dto.PostRequest;
import wooteco.prolog.post.application.dto.PostResponse;
import wooteco.prolog.post.application.dto.PostsResponse;
import wooteco.prolog.post.domain.Post;
import wooteco.prolog.post.domain.repository.PostRepository;
import wooteco.prolog.post.exception.PostArgumentException;
import wooteco.prolog.post.exception.PostNotFoundException;
import wooteco.prolog.posttag.application.PostTagService;
import wooteco.prolog.posttag.domain.PostTag;
import wooteco.prolog.tag.application.TagService;
import wooteco.prolog.tag.domain.Tag;
import wooteco.prolog.tag.domain.Tags;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;
    private final MissionService missionService;
    private final PostTagService postTagService;
    private final MemberService memberService;
    private final TagService tagService;

    public PostsResponse findPostsWithFilter(List<Long> missionIds, List<Long> tagIds,
            Pageable pageable) {
        missionIds = nullToEmptyList(missionIds);
        tagIds = nullToEmptyList(tagIds);

        List<Mission> missions = missionService.findByIds(missionIds);
        List<Tag> tags = tagService.findByIds(tagIds);
        List<PostTag> postTags = postTagService.findByTags(tags);
        //Todo : explosion zone 폭탄 제거
        Page<Post> posts = elseIfExplosionZone(missionIds, tagIds, missions, postTags, pageable);

        return PostsResponse.of(posts);
    }

    private Page<Post> elseIfExplosionZone(List<Long> missionIds, List<Long> tagIds,
            List<Mission> missions,
            List<PostTag> postTags,
            Pageable pageable) {
        if (isNullOrEmpty(missionIds) && isNullOrEmpty(tagIds)) {
            return postRepository.findAll(pageable);
        } else if (!isNullOrEmpty(missionIds) && !isNullOrEmpty(tagIds)) {
            return postRepository.findByMissionInAndPostTagsIn(missions, postTags, pageable);
        } else if (isNullOrEmpty(missionIds)) {
            return postRepository.findByPostTagsIn(postTags, pageable);
        } else {
            return postRepository.findByMissionIn(missions, pageable);
        }
    }

    public PostsResponse findPostsOf(String username, Pageable pageable) {
        Member member = memberService.findByUsername(username);
        return PostsResponse.of(postRepository.findByMember(member, pageable));
    }

    private boolean isNullOrEmpty(List<Long> ids) {
        return Objects.isNull(ids) || ids.isEmpty();
    }

    private List<Long> nullToEmptyList(List<Long> filters) {
        if (Objects.isNull(filters)) {
            filters = Collections.emptyList();
        }
        return filters;
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

        Post requestedPost = new Post(member,
                postRequest.getTitle(),
                postRequest.getContent(),
                mission);

        Post createdPost = postRepository.save(requestedPost);
        createdPost.addTags(tags);

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

        Mission mission = missionService.findById(postRequest.getMissionId());
        Tags tags = tagService.findOrCreate(postRequest.getTags());
        post.update(postRequest.getTitle(), postRequest.getContent(), mission, tags);
    }

    @Transactional
    public void deletePost(Member member, Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);
        post.validateAuthor(member);

        postRepository.delete(post);
    }
}
