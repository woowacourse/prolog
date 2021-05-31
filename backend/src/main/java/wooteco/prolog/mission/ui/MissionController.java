package wooteco.prolog.mission.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wooteco.prolog.mission.application.MissionService;
import wooteco.prolog.mission.application.dto.MissionRequest;
import wooteco.prolog.mission.application.dto.MissionResponse;

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
    public ResponseEntity<MissionResponse> createmission(@RequestBody MissionRequest missionRequest) {
        MissionResponse missionResponse = missionService.create(missionRequest);
        return ResponseEntity.ok(missionResponse);
    }
}