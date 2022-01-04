package wooteco.prolog.studylog.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wooteco.prolog.login.domain.AuthMemberPrincipal;
import wooteco.prolog.login.ui.LoginMember;
import wooteco.prolog.studylog.application.StudylogLikeService;
import wooteco.prolog.studylog.application.dto.StudylogLikeResponse;

@RestController
@RequestMapping("/studylogs")
public class StudylogLikeController {

    private final StudylogLikeService studylogLikeService;

    public StudylogLikeController(StudylogLikeService studylogLikeService) {
        this.studylogLikeService = studylogLikeService;
    }

    @PostMapping("{studylogId}/likes")
    public ResponseEntity<StudylogLikeResponse> likeStudylog(
        @AuthMemberPrincipal LoginMember member,
        @PathVariable Long studylogId
    ) {
        StudylogLikeResponse studylogLikeResponse =
            studylogLikeService.likeStudylog(member.getId(), studylogId, member.isMember());
        return ResponseEntity.ok(studylogLikeResponse);
    }

    @DeleteMapping("{studylogId}/likes")
    public ResponseEntity<StudylogLikeResponse> unlikeStudylog(
        @AuthMemberPrincipal LoginMember member,
        @PathVariable Long studylogId
    ) {
        StudylogLikeResponse studylogLikeResponse =
            studylogLikeService.unlikeStudylog(member.getId(), studylogId, member.isMember());
        return ResponseEntity.ok(studylogLikeResponse);
    }
}
