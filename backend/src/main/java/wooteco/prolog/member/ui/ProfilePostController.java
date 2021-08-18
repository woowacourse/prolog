package wooteco.prolog.member.ui;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wooteco.prolog.member.application.MemberService;
import wooteco.prolog.member.application.dto.MemberResponse;
import wooteco.prolog.post.application.PostService;
import wooteco.prolog.post.application.dto.PageRequest;
import wooteco.prolog.post.application.dto.PostSearchRequest;
import wooteco.prolog.post.application.dto.PostsResponse;

@RestController
@RequestMapping("/members")
public class ProfilePostController {
    private PostService postService;
    private MemberService memberService;

    public ProfilePostController(PostService postService, MemberService memberService) {
        this.postService = postService;
        this.memberService = memberService;
    }

    @GetMapping(value = "/{username}/posts", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PostsResponse> findAllPostsOfMine(@PathVariable String username, @ModelAttribute PageRequest pageRequest, PostSearchRequest postSearchRequest) {
        PostsResponse posts = postService.findPostsOf(username, pageRequest, postSearchRequest);
        return ResponseEntity.ok().body(posts);
    }

    @GetMapping(value = "/{username}/profile", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MemberResponse> findMemberProfile(@PathVariable String username) {
        MemberResponse member = memberService.findMemberByUsername(username);
        return ResponseEntity.ok().body(member);
    }
}
