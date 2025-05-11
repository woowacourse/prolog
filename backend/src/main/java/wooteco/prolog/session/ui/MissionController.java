package wooteco.prolog.session.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import wooteco.prolog.login.aop.MemberOnly;
import wooteco.prolog.login.domain.AuthMemberPrincipal;
import wooteco.prolog.login.ui.LoginMember;
import wooteco.prolog.session.application.MissionService;
import wooteco.prolog.session.application.dto.MissionRequest;
import wooteco.prolog.session.application.dto.MissionResponse;

import java.util.List;

@RestController
@RequestMapping("/missions")
public class MissionController {

    private final MissionService missionService;

    public MissionController(MissionService missionService) {
        this.missionService = missionService;
    }

    @GetMapping
    public ResponseEntity<List<MissionResponse>> showMissions(
        @RequestParam(required = false) final Long sessionId
    ) {
        if (sessionId == null) {
            return ResponseEntity.ok(missionService.findAll());
        } else {
            return ResponseEntity.ok(missionService.findAllBySessionId(sessionId));
        }
    }

    @MemberOnly
    @GetMapping("/mine")
    public ResponseEntity<List<MissionResponse>> findMyMissions(
        @AuthMemberPrincipal LoginMember loginMember) {
        List<MissionResponse> responses = missionService.findMyMissions(loginMember);
        return ResponseEntity.ok(responses);
    }

    @PostMapping
    public ResponseEntity<MissionResponse> createMission(
        @RequestBody MissionRequest missionRequest) {
        MissionResponse missionResponse = missionService.create(missionRequest);
        return ResponseEntity.ok(missionResponse);
    }
}
