package wooteco.prolog.studylog.ui;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wooteco.prolog.login.aop.MemberOnly;
import wooteco.prolog.login.domain.AuthMemberPrincipal;
import wooteco.prolog.login.ui.LoginMember;
import wooteco.prolog.studylog.application.StudylogService;
import wooteco.prolog.studylog.application.StudylogSessionService;
import wooteco.prolog.studylog.application.dto.StudylogMissionRequest;
import wooteco.prolog.studylog.application.dto.StudylogSessionRequest;

@RestController
@RequestMapping("/studylogs")
@AllArgsConstructor
public class StudylogSessionController {

    private final StudylogSessionService studylogSessionService;
    private final StudylogService studylogService;

    @PutMapping("/{id}/sessions")
    @MemberOnly
    public ResponseEntity<Void> updateStudylogSession(@AuthMemberPrincipal LoginMember member,
        @PathVariable Long id,
        @RequestBody StudylogSessionRequest studylogSessionRequest) {
        studylogService.updateStudylogSession(member.getId(), id, studylogSessionRequest);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/missions")
    @MemberOnly
    public ResponseEntity<Void> updateStudylogMissions(@AuthMemberPrincipal LoginMember member,
        @PathVariable Long id,
        @RequestBody StudylogMissionRequest studylogMissionRequest) {
        studylogService.updateStudylogMission(member.getId(), id, studylogMissionRequest);
        return ResponseEntity.ok().build();
    }

    // admin only
    @GetMapping("/sessions/sync")
    public ResponseEntity<String> syncStudylogMission() {
        studylogSessionService.syncStudylogSession();
        return ResponseEntity.ok().body("SUCCESS");
    }
}
