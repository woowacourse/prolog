package wooteco.prolog.studylog.ui;

import java.time.LocalDate;
import java.util.ArrayList;
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
import wooteco.prolog.studylog.application.StudylogService;
import wooteco.prolog.studylog.application.dto.StudylogsResponse;

@RestController
@AllArgsConstructor
@RequestMapping("/members")
public class ProfileStudylogController {

    private StudylogService studylogService;
    private MemberService memberService;

    @Deprecated
    @GetMapping(value = "/{username}/posts", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StudylogsResponse> findAllPostsOfMine(@PathVariable String username,
                                                                PostFilterRequest postFilterRequest,
                                                                @PageableDefault(size = 20, direction = Direction.DESC, sort = "id") Pageable pageable) {
        final StudylogsResponse posts = studylogService.findPostsWithoutKeyword(
            postFilterRequest.levels,
            postFilterRequest.missions,
            postFilterRequest.tags,
            Collections.singletonList(username),
            new ArrayList<>(),
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
        private List<Long> levels;
        private List<Long> missions;
        private List<Long> tags;
        private LocalDate startDate;
        private LocalDate endDate;
    }
}
