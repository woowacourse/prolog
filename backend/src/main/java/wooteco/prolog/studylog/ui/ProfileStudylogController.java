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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wooteco.prolog.login.domain.AuthMemberPrincipal;
import wooteco.prolog.login.ui.LoginMember;
import wooteco.prolog.member.application.MemberService;
import wooteco.prolog.member.application.dto.MemberResponse;
import wooteco.prolog.member.application.dto.ProfileIntroRequest;
import wooteco.prolog.member.application.dto.ProfileIntroResponse;
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
                                                                StudylogFilterRequest studylogFilterRequest,
                                                                @PageableDefault(size = 20, direction = Direction.DESC, sort = "id") Pageable pageable) {
        final StudylogsResponse studylogs = studylogService.findStudylogsWithoutKeyword(
            studylogFilterRequest.levels,
            studylogFilterRequest.missions,
            studylogFilterRequest.tags,
            Collections.singletonList(username),
            new ArrayList<>(),
            studylogFilterRequest.startDate,
            studylogFilterRequest.endDate,
            pageable,
            null
        );
        return ResponseEntity.ok().body(studylogs);
    }

    @GetMapping(value = "/{username}/studylogs", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StudylogsResponse> findAllStudylogsOfMine(@PathVariable String username,
                                                                    StudylogFilterRequest studylogFilterRequest,
                                                                    @PageableDefault(size = 20, direction = Direction.DESC, sort = "id") Pageable pageable) {
        final StudylogsResponse studylogs = studylogService.findStudylogsWithoutKeyword(
            studylogFilterRequest.levels,
            studylogFilterRequest.missions,
            studylogFilterRequest.tags,
            Collections.singletonList(username),
            new ArrayList<>(),
            studylogFilterRequest.startDate,
            studylogFilterRequest.endDate,
            pageable,
            null
        );
        return ResponseEntity.ok().body(studylogs);
    }

    @GetMapping(value = "/{username}/profile", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MemberResponse> findMemberProfile(@PathVariable String username) {
        MemberResponse member = memberService.findMemberResponseByUsername(username);
        return ResponseEntity.ok().body(member);
    }

    @GetMapping(value = "/{username}/profile-intro", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProfileIntroResponse> findMemberProfileIntro(@PathVariable String username) {
        ProfileIntroResponse profileIntro = memberService.findProfileIntro(username);
        return ResponseEntity.ok().body(profileIntro);
    }

    @PutMapping(value = "/{username}/profile-intro", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> findMemberProfileIntro(@AuthMemberPrincipal LoginMember member, @PathVariable String username,
                                                                       @RequestBody ProfileIntroRequest updateRequest) {
        memberService.updateProfileIntro(member, username, updateRequest);
        return ResponseEntity.ok().build();
    }

    @Data
    public static class StudylogFilterRequest {

        private List<Long> levels;
        private List<Long> missions;
        private List<Long> tags;
        private LocalDate startDate;
        private LocalDate endDate;
    }
}
