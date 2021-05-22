package wooteco.prolog.post.service;

import org.springframework.stereotype.Service;
import wooteco.prolog.post.dao.PostDao;
import wooteco.prolog.post.domain.Post;
import wooteco.prolog.post.web.controller.dto.PostRequest;
import wooteco.prolog.post.web.controller.dto.PostResponse;

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

    public PostResponse insertLogs(List<PostRequest> postRequests) {
        List<Post> posts = postRequests.stream()
                .map(PostRequest::toEntity)
                .collect(Collectors.toList());
        Post firstPost = postDao.insert(posts.get(0));
        postDao.insert(posts.subList(1, posts.size())); // TODO : Posts가 1개인 경우 실행되지 않음
        return PostResponse.of(firstPost);
    }

    public PostResponse findById(Long id) {
        Post post = postDao.findById(id);
        return PostResponse.of(post);
    }
}
