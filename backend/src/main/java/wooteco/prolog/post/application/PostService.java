package wooteco.prolog.post.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wooteco.prolog.login.application.dto.MemberResponse;
import wooteco.prolog.login.domain.Member;
import wooteco.prolog.mission.application.MissionService;
import wooteco.prolog.mission.application.dto.MissionResponse;
import wooteco.prolog.post.application.dto.PostRequest;
import wooteco.prolog.post.application.dto.PostResponse;
import wooteco.prolog.post.dao.PostDao;
import wooteco.prolog.post.domain.Post;
import wooteco.prolog.post.exception.AuthorNotValidException;
import wooteco.prolog.post.exception.PostArgumentException;
import wooteco.prolog.tag.application.TagService;
import wooteco.prolog.tag.dto.TagResponse;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class PostService {
    private final PostDao postDao;
    private final MissionService missionService;
    private final TagService tagService;

    public PostService(PostDao postDao, MissionService missionService, TagService tagService) {
        this.postDao = postDao;
        this.missionService = missionService;
        this.tagService = tagService;
    }

    public List<PostResponse> findAll() {
        List<Post> posts = postDao.findAll();
        return posts.stream()
                .map(post -> toResponse(post))
                .collect(Collectors.toList());
    }

    public List<PostResponse> findAllOfMine(Member member) {
        List<Post> posts = postDao.findAllByMemberId(member.getId());
        return posts.stream()
                .map(post -> toResponse(post))
                .collect(Collectors.toList());
    }

    public List<PostResponse> findPostsWithFilter(List<Long> missions, List<Long> tags) {
        missions = nullToEmptyList(missions);
        tags = nullToEmptyList(tags);

        List<Post> posts = postDao.findWithFilter(missions, tags);
        return posts.stream()
                .map(post -> toResponse(post))
                .collect(Collectors.toList());
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
        List<TagResponse> tagResponses = tagService.create(postRequest.getTags());
        List<Long> tagIds = getTagIds(tagResponses);

        Post requestedPost = new Post(member, postRequest.getTitle(), postRequest.getContent(), postRequest.getMissionId(), tagIds);
        Post createdPost = postDao.insert(requestedPost);
        tagService.addTagToPost(createdPost.getId(), tagIds);

        MissionResponse missionResponse = missionService.findById(requestedPost.getMissionId());
        return new PostResponse(
                createdPost.getId(),
                MemberResponse.of(createdPost.getMember()),
                createdPost.getCreatedAt(),
                createdPost.getUpdatedAt(),
                missionResponse,
                createdPost.getTitle(),
                createdPost.getContent(),
                tagResponses);
    }

    public PostResponse findById(Long id) {
        Post post = postDao.findById(id);
        return toResponse(post);
    }

    private PostResponse toResponse(Post post) {
        List<TagResponse> tagResponses = tagService.getTagsOfPost(post.getId());
        MissionResponse missionResponse = missionService.findById(post.getMissionId());
        return new PostResponse(post, missionResponse, tagResponses);
    }

    public void updatePost(Member member, Long id, PostRequest postRequest) {
        PostResponse post = findById(id);
        validateAuthor(member, post);

        List<TagResponse> tags = tagService.create(postRequest.getTags());
        List<Long> tagIds = getTagIds(tags);

        Post updatedPost = new Post(
                member,
                postRequest.getTitle(),
                postRequest.getContent(),
                postRequest.getMissionId(),
                tagIds
            );
        postDao.update(id, updatedPost);
        tagService.addTagToPost(id, tagIds);

        removeTagsByUpdate(id, tagIds);
    }

    private void removeTagsByUpdate(Long id, List<Long> tagIds) {
        List<TagResponse> originTags = tagService.getTagsOfPost(id);
        List<Long> originTagIds = getTagIds(originTags);
        List<Long> removedTagIds = originTagIds.stream()
            .filter(tagId -> !tagIds.contains(tagId))
            .collect(Collectors.toList());
        tagService.removeTagFromPost(id, removedTagIds);
    }

    private List<Long> getTagIds(List<TagResponse> originTags) {
        return originTags.stream()
            .map(TagResponse::getId)
            .collect(Collectors.toList());
    }

    private void validateAuthor(Member member, PostResponse post) {
        if (!member.getId().equals(post.getAuthor().getId())) {
            throw new AuthorNotValidException("작성자만 수정할 수 있습니다.");
        }
    }
    public void deletePost(Member member, Long id) {
        PostResponse post = findById(id);
        if (post.getAuthor().getId() != member.getId()) {
            throw new RuntimeException();
        }

        tagService.deletePostTagByPostId(id);
        postDao.deleteById(id);
    }
}
