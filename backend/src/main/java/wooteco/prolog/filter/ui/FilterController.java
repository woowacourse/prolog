package wooteco.prolog.filter.ui;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import wooteco.prolog.mission.application.MissionService;
import wooteco.prolog.mission.application.dto.MissionResponse;
import wooteco.prolog.filter.application.dto.FilterResponse;
import wooteco.prolog.tag.TagService;
import wooteco.prolog.tag.dto.TagResponse;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/filters")
@AllArgsConstructor
public class FilterController {

    private final MissionService missionService;
    private final TagService tagService;

    @GetMapping
    public ResponseEntity<FilterResponse> showAll() {
        List<MissionResponse> missionResponses = missionService.findAll();
        List<TagResponse> tagResponses = tagService.findAll();
        return ResponseEntity.ok().body(new FilterResponse(missionResponses, tagResponses));
    }
}
