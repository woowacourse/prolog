package wooteco.prolog.post.application;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wooteco.prolog.member.application.dto.MemberResponse;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.mission.application.MissionService;
import wooteco.prolog.mission.application.dto.MissionResponse;
import wooteco.prolog.post.application.dto.PageRequest;
import wooteco.prolog.post.application.dto.PostRequest;
import wooteco.prolog.post.application.dto.PostResponse;
import wooteco.prolog.post.application.dto.PostSearchRequest;
import wooteco.prolog.post.application.dto.PostsResponse;
import wooteco.prolog.post.dao.PostDao;
import wooteco.prolog.post.domain.Post;
import wooteco.prolog.post.exception.AuthorNotValidException;
import wooteco.prolog.post.exception.PostArgumentException;
import wooteco.prolog.post.exception.PostNotFoundException;
import wooteco.prolog.tag.application.TagService;
import wooteco.prolog.tag.dto.TagResponse;

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
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public PostsResponse findPostsWithFilter(List<Long> missions, List<Long> tags, PageRequest pageRequest) {
        missions = nullToEmptyList(missions);
        tags = nullToEmptyList(tags);

        List<Post> posts = postDao.findWithFilter(missions, tags, pageRequest);
        List<PostResponse> postResponses = posts.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());

        int totalCount = postDao.count(missions, tags);
        int totalPage = pageRequest.calculateTotalPage(totalCount);
        return new PostsResponse(postResponses, totalCount, totalPage, pageRequest.getPage());
    }

    public List<PostResponse> findAllOfMine(Member member) {
        List<Post> posts = postDao.findAllByMemberId(member.getId());
        return posts.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public PostsResponse findPostsOf(String username, PageRequest pageRequest, PostSearchRequest postSearchRequest) {
        List<Post> posts = postDao.findAllByUsername(username, pageRequest);

        // TODO : JPA 완성 시 동적 쿼리로 이동
        posts = filterBySearch(posts, postSearchRequest);

        List<PostResponse> postResponses = posts.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());

        int totalCount = postDao.countByUsername(username);
        int totalPage = pageRequest.calculateTotalPage(totalCount);
        return new PostsResponse(postResponses, totalCount, totalPage, pageRequest.getPage());
    }

    // TODO : JPA 완성 시 동적 쿼리로 이동
    private List<Post> filterBySearch(List<Post> posts,
            PostSearchRequest postSearchRequest) {
        Stream<Post> resultStream = posts.stream();
        if(postSearchRequest.getDate() != null) {
            resultStream = resultStream.filter(post -> post.getCreatedAt().toLocalDate().isEqual(postSearchRequest.getDate()));
        }
        if(postSearchRequest.getTagId() != null) {
            resultStream = resultStream.filter(post -> post.getTagIds().contains(postSearchRequest.getTagId()));
        }
        return resultStream.collect(Collectors.toList());
    }

    public PostsResponse findPostsWithFilter(List<Long> missions, List<Long> tags) {
        return findPostsWithFilter(missions, tags, new PageRequest());
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
        if(Objects.isNull(post)) {
            throw new PostNotFoundException();
        }
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
        if (!post.getAuthor().getId().equals(member.getId())) {
            throw new RuntimeException();
        }

        tagService.deletePostTagByPostId(id);
        postDao.deleteById(id);
    }
}
