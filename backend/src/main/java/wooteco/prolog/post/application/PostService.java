package wooteco.prolog.post.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wooteco.prolog.category.application.CategoryService;
import wooteco.prolog.category.application.dto.CategoryResponse;
import wooteco.prolog.post.application.dto.PostRequest;
import wooteco.prolog.post.application.dto.PostResponse;
import wooteco.prolog.post.dao.PostDao;
import wooteco.prolog.post.domain.Post;
import wooteco.prolog.post.exception.PostArgumentException;
import wooteco.prolog.tag.application.TagService;
import wooteco.prolog.tag.application.dto.TagResponse;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {
    private final PostDao postDao;
    private final CategoryService categoryService;
    private final TagService tagService;

    public PostService(PostDao postDao, CategoryService categoryService, TagService tagService) {
        this.postDao = postDao;
        this.categoryService = categoryService;
        this.tagService = tagService;
    }

    public List<PostResponse> findAll() {
        List<Post> posts = postDao.findAll();
        return posts.stream()
                .map(post -> findById(post.getId()))
                .collect(Collectors.toList());
    }

    @Transactional
    public List<PostResponse> insertLogs(List<PostRequest> postRequests) {
        if (postRequests.size() == 0) {
            throw new PostArgumentException("최소 1개의 글이 있어야 합니다.");
        }

        return postRequests.stream()
                .map(this::insertLog)
                .collect(Collectors.toList());
    }

    private PostResponse insertLog(PostRequest postRequest) {
        List<TagResponse> tagResponses = tagService.create(postRequest.getTags());
        List<Long> tagIds = tagResponses.stream()
                .map(TagResponse::getId)
                .collect(Collectors.toList());


        Post requestedPost = postRequest.toEntity();
        Post createdPost = postDao.insert(requestedPost);
        tagService.addTagToPost(createdPost.getId(), tagIds);

        CategoryResponse categoryResponse = categoryService.findById(requestedPost.getCategoryId());
        return new PostResponse(
                createdPost.getId(),
                createdPost.getAuthor(),
                createdPost.getCreatedAt(),
                createdPost.getUpdatedAt(),
                categoryResponse,
                createdPost.getTitle(),
                createdPost.getContent(),
                tagResponses);
    }

    public PostResponse findById(Long id) {
        Post post = postDao.findById(id);
        List<TagResponse> tagResponses = tagService.getTagsOfPost(id);
        CategoryResponse categoryResponse = categoryService.findById(post.getCategoryId());
        return new PostResponse(post, categoryResponse, tagResponses);
    }
}
