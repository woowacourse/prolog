package wooteco.prolog.member.ui;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wooteco.prolog.member.application.MemberService;
import wooteco.prolog.member.application.dto.MemberResponse;
import wooteco.prolog.post.application.PostService;
import wooteco.prolog.post.application.dto.PostsResponse;

@RestController
@AllArgsConstructor
@RequestMapping("/members")
public class ProfilePostController {

    private PostService postService;
    private MemberService memberService;

    @GetMapping(value = "/{username}/posts", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PostsResponse> findAllPostsOfMine(@PathVariable String username,
        PostFilterRequest postFilterRequest,
        @PageableDefault(size = 20, direction = Direction.DESC, sort = "id") Pageable pageable) {
        final PostsResponse posts = postService.findPostsWithFilter(
            postFilterRequest.levelIds,
            postFilterRequest.missionIds,
            postFilterRequest.tagIds,
            Collections.singletonList(username),
            postFilterRequest.startDate,
            postFilterRequest.endDate,
            pageable
        );
        return ResponseEntity.ok().body(posts);
    }

    @GetMapping(value = "/{username}/profile", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MemberResponse> findMemberProfile(@PathVariable String username) {
        MemberResponse member = memberService.findMemberResponseByUsername(username);
        return ResponseEntity.ok().body(member);
    }

    @Data
    public static class PostFilterRequest {
        private List<Long> levelIds;
        private List<Long> missionIds;
        private List<Long> tagIds;
        private LocalDateTime startDate;
        private LocalDateTime endDate;
    }
}
