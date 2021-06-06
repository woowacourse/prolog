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
import wooteco.prolog.post.exception.PostArgumentException;
import wooteco.prolog.tag.TagService;
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
                .map(post -> findById(post.getId()))
                .collect(Collectors.toList());
    }

    public List<PostResponse> findWithFilter(List<Long> missions, List<Long> tags) {
        missions = nullToEmptyList(missions);
        tags = nullToEmptyList(tags);

        List<Post> posts = postDao.findWithFilter(missions, tags);
        return posts.stream()
                .map(post -> findById(post.getId()))
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
            throw new PostArgumentException("최소 1개의 글이 있어야 합니다.");
        }

        return postRequests.stream()
                .map(postRequest -> insertPost(member, postRequest))
                .collect(Collectors.toList());
    }

    private PostResponse insertPost(Member member, PostRequest postRequest) {
        List<TagResponse> tagResponses = tagService.create(postRequest.getTags());
        List<Long> tagIds = tagResponses.stream()
                .map(TagResponse::getId)
                .collect(Collectors.toList());

        Post requestedPost = postRequest.toEntity(member);
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
        List<TagResponse> tagResponses = tagService.getTagsOfPost(id);
        MissionResponse missionResponse = missionService.findById(post.getMissionId());
        return new PostResponse(post, missionResponse, tagResponses);
    }

}
