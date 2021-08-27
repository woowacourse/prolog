package wooteco.prolog.studylog.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wooteco.prolog.studylog.application.MissionService;
import wooteco.prolog.studylog.application.dto.MissionRequest;
import wooteco.prolog.studylog.application.dto.MissionResponse;

import java.util.List;

@RestController
@RequestMapping("/missions")
public class MissionController {

    private final MissionService missionService;

    public MissionController(MissionService missionService) {
        this.missionService = missionService;
    }

    @GetMapping
    public ResponseEntity<List<MissionResponse>> showCategories() {
        List<MissionResponse> responses = missionService.findAll();
        return ResponseEntity.ok(responses);
    }

    @PostMapping
    public ResponseEntity<MissionResponse> createMission(
            @RequestBody MissionRequest missionRequest) {
        MissionResponse missionResponse = missionService.create(missionRequest);
        return ResponseEntity.ok(missionResponse);
    }
}