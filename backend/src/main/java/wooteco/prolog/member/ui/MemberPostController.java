package wooteco.prolog.member.ui;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wooteco.prolog.login.domain.AuthMemberPrincipal;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.post.application.PostService;
import wooteco.prolog.post.application.dto.PostResponse;

import java.util.List;

@RestController
@RequestMapping("/members")
public class MemberPostController {
    private PostService postService;

    public MemberPostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping(value = "/{username}/posts", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PostResponse>> findAllPostsOfMine(@PathVariable String username) {
        List<PostResponse> posts = postService.findPostsOf(username);
        return ResponseEntity.ok().body(posts);
    }
}
