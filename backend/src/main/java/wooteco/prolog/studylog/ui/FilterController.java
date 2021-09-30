package wooteco.prolog.studylog.ui;

import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wooteco.prolog.report.application.LevelService;
import wooteco.prolog.report.application.MissionService;
import wooteco.prolog.report.application.TagService;
import wooteco.prolog.report.application.dto.FilterResponse;
import wooteco.prolog.report.application.dto.LevelResponse;
import wooteco.prolog.report.application.dto.MissionResponse;
import wooteco.prolog.report.application.dto.TagResponse;

@RestController
@RequestMapping("/filters")
@AllArgsConstructor
public class FilterController {

    private final LevelService levelService;
    private final MissionService missionService;
    private final TagService tagService;

    @GetMapping
    public ResponseEntity<FilterResponse> showAll() {
        List<LevelResponse> levelResponses = levelService.findAll();
        List<MissionResponse> missionResponses = missionService.findAll();
        List<TagResponse> tagResponses = tagService.findTagsIncludedInPost();
        return ResponseEntity.ok()
            .body(new FilterResponse(levelResponses, missionResponses, tagResponses));
    }
}
