package wooteco.prolog.post.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wooteco.prolog.post.application.dto.PostRequest;
import wooteco.prolog.post.application.dto.PostResponse;
import wooteco.prolog.post.dao.PostDao;
import wooteco.prolog.post.domain.Post;
import wooteco.prolog.post.exception.PostArgumentException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {
    private final PostDao postDao;

    public PostService(PostDao postDao) {
        this.postDao = postDao;
    }

    public List<PostResponse> findAll() {
        List<Post> all = postDao.findAll();
        return all.stream()
                .map(PostResponse::of)
                .collect(Collectors.toList());
    }

    @Transactional
    public PostResponse insertLogs(List<PostRequest> postRequests) {
        if (postRequests.size() == 0) {
            throw new PostArgumentException("최소 1개의 글이 있어야 합니다.");
        }

        List<Post> posts = postRequests.stream()
                .map(PostRequest::toEntity)
                .collect(Collectors.toList());
        Post firstPost = postDao.insert(posts.get(0));

        try {
            postDao.insert(posts.subList(1, posts.size()));
        } catch (IndexOutOfBoundsException e) {
            return PostResponse.of(firstPost);
        }

        return PostResponse.of(firstPost);
    }

    public PostResponse findById(Long id) {
        Post post = postDao.findById(id);
        return PostResponse.of(post);
    }
}
