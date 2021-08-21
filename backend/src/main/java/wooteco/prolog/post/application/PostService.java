package wooteco.prolog.post.application;

import static java.util.stream.Collectors.toList;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.mission.application.MissionService;
import wooteco.prolog.mission.application.dto.MissionResponse;
import wooteco.prolog.mission.domain.Mission;
import wooteco.prolog.post.application.dto.PageRequest;
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
import wooteco.prolog.tag.dto.TagResponse;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;
    private final MissionService missionService;
    private final PostTagService postTagService;
    private final TagService tagService;

    public List<PostResponse> findAll() {
        List<Post> posts = postRepository.findAll();
        return posts.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public PostsResponse findPostsWithFilter(List<Long> missionIds, List<Long> tagIds,
            Pageable pageable) {
        missionIds = nullToEmptyList(missionIds);
        tagIds = nullToEmptyList(tagIds);

        List<Mission> missions = missionService.findByIds(missionIds);
        List<Tag> tags = tagService.findByIds(tagIds);
        List<PostTag> postTags = postTagService.findByTags(tags);
        //Todo : missions와 postTags가 없을때 (필터가 없을때) 전체 조회
        //두 개 다 있을 땐 AND, MissonId만 있을땐 Mission IN, Tag만 있을 땐 Tag IN, 둘다 없으면 전체 조회
        Page<Post> posts = elseIfExplosionZone(missionIds, tagIds, missions, postTags, pageable);
//        List<Post> posts = postRepository.findWithFilter(missions, tags, pageRequest);
        Page<PostResponse> page = posts.map(this::toResponse);
//        Page<PostResponse> postResponses = posts.stream()
//                .map(this::toResponse)
//                .collect(Collectors.toList());

        return new PostsResponse(page.getContent(), page.getTotalElements(), page.getTotalPages(), pageable.getPageNumber());
    }

    private Page<Post> elseIfExplosionZone(List<Long> missionIds, List<Long> tagIds, List<Mission> missions,
            List<PostTag> postTags, Pageable pageable) {
        if(isNullOrEmpty(missionIds) && isNullOrEmpty(tagIds)){
            return postRepository.findAll(pageable);
        } else if (!isNullOrEmpty(missionIds) && !isNullOrEmpty(tagIds)) {
            return postRepository.findByMissionInAndPostTagsIn(missions, postTags, pageable);
        } else if (isNullOrEmpty(missionIds)) {
            return postRepository.findByPostTagsIn(postTags, pageable);
        } else {
            return postRepository.findByMissionIn(missions, pageable);
        }
    }

    public PostsResponse findPostsOf(String username, PageRequest pageRequest) {
//        List<Post> posts = postDao.findAllByUsername(username, pageRequest);
//        List<PostResponse> postResponses = posts.stream()
//                .map(this::toResponse)
//                .collect(Collectors.toList());
//
//        int totalCount = postDao.countByUsername(username);
//        int totalPage = pageRequest.calculateTotalPage(totalCount);
//        return new PostsResponse(postResponses, totalCount, totalPage, pageRequest.getPage());
        return null;
    }

    public PostsResponse findPostsWithFilter(List<Long> missions, List<Long> tags) {
        return findPostsWithFilter(missions, tags, new Pageable() {
            @Override
            public int getPageNumber() {
                return 0;
            }

            @Override
            public int getPageSize() {
                return 0;
            }

            @Override
            public long getOffset() {
                return 0;
            }

            @Override
            public Sort getSort() {
                return null;
            }

            @Override
            public Pageable next() {
                return null;
            }

            @Override
            public Pageable previousOrFirst() {
                return null;
            }

            @Override
            public Pageable first() {
                return null;
            }

            @Override
            public boolean hasPrevious() {
                return false;
            }
        });
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
        Tags tags = tagService.create(postRequest.getTags());
        Mission mission = missionService.findById(postRequest.getMissionId());

        Post requestedPost = new Post(member, postRequest.getTitle(), postRequest.getContent(),
                mission);
        Post createdPost = postRepository.save(requestedPost);
        postTagService.addTagToPost(createdPost, tags);
        // TODO: 연관관계 매핑하기!
        return PostResponse.of(createdPost);
    }

    public PostResponse findById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(PostNotFoundException::new);

        return PostResponse.of(post);
    }

    private PostResponse toResponse(Post post) {
        // TODO : n+1 조심
        List<PostTag> postTags = postTagService.getPostTagsOfPost(post);
        final List<Tag> tags = postTags.stream()
                .map(PostTag::getTag)
                .collect(toList());

        return new PostResponse(post, MissionResponse.of(post.getMission()), toResponse(tags));
    }

    private List<TagResponse> toResponse(List<Tag> tags) {
        return tags.stream()
                .map(TagResponse::of)
                .collect(Collectors.toList());
    }

    @Transactional
    public void updatePost(Member member, Long postId, PostRequest postRequest) {
        Post post = postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);
        post.validateAuthor(member);

        postTagService.removeTagsOfPost(post);
        Mission mission = missionService.findById(postRequest.getMissionId());
        Tags tags = tagService.create(postRequest.getTags());
        post.update(postRequest.getTitle(), postRequest.getContent(), mission, tags);

        postTagService.updateTagToPost(post);
    }

    public void deletePost(Member member, Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);
        post.validateAuthor(member);

        postTagService.removeTagsOfPost(post);
        postRepository.delete(post);
    }
}
