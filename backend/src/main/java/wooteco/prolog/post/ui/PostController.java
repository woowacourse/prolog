package wooteco.prolog.post.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wooteco.prolog.login.domain.AuthMemberPrincipal;
import wooteco.prolog.login.domain.Member;
import wooteco.prolog.post.application.PostService;
import wooteco.prolog.post.application.dto.PostRequest;
import wooteco.prolog.post.application.dto.PostResponse;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public ResponseEntity<Void> createPost(@AuthMemberPrincipal Member member, @RequestBody List<PostRequest> postRequests) {
        List<PostResponse> postResponse = postService.insertPosts(member, postRequests);
        return ResponseEntity.created(URI.create("/posts/" + postResponse.get(0).getId())).build();
    }

    @GetMapping
    public ResponseEntity<List<PostResponse>> showAll(
            @RequestParam(required = false) List<Long> missions,
            @RequestParam(required = false) List<Long> tags
    ) {
        List<PostResponse> postResponses = postService.findPostsWithFilter(missions, tags);

        return ResponseEntity.ok(postResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> showPost(@PathVariable Long id) {
        PostResponse postResponse = postService.findById(id);
        return ResponseEntity.ok(postResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@AuthMemberPrincipal Member member, @PathVariable Long id) {
        postService.deletePost(member, id);
        return ResponseEntity.noContent().build();
    }
}