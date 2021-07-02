package wooteco.prolog.post.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wooteco.prolog.post.application.dto.PageRequest;
import wooteco.prolog.login.domain.AuthMemberPrincipal;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.post.application.PostService;
import wooteco.prolog.post.application.dto.PostResponse;
import wooteco.prolog.post.application.dto.PostRequest;
import wooteco.prolog.post.application.dto.PostDataResponse;

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
        List<PostDataResponse> postDataResponse = postService.insertPosts(member, postRequests);
        return ResponseEntity.created(URI.create("/posts/" + postDataResponse.get(0).getId())).build();
    }

    @GetMapping
    public ResponseEntity<PostResponse> showAll(
            @RequestParam(required = false) List<Long> missions,
            @RequestParam(required = false) List<Long> tags,
            @ModelAttribute PageRequest pageRequest
    ) {
        PostResponse postResponse = postService.findPostsWithFilter(missions, tags, pageRequest);

        return ResponseEntity.ok(postResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDataResponse> showPost(@PathVariable Long id) {
        PostDataResponse postDataResponse = postService.findById(id);
        return ResponseEntity.ok(postDataResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updatePost(
            @AuthMemberPrincipal Member member,
            @PathVariable Long id,
            @RequestBody PostRequest postRequest
    ) {
        postService.updatePost(member, id, postRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@AuthMemberPrincipal Member member, @PathVariable Long id) {
        postService.deletePost(member, id);
        return ResponseEntity.noContent().build();
    }
}