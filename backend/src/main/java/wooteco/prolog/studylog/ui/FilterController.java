package wooteco.prolog.studylog.ui;

import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wooteco.prolog.member.application.MemberService;
import wooteco.prolog.member.application.dto.MemberResponse;
import wooteco.prolog.studylog.application.LevelService;
import wooteco.prolog.studylog.application.MissionService;
import wooteco.prolog.studylog.application.TagService;
import wooteco.prolog.studylog.application.dto.FilterResponse;
import wooteco.prolog.studylog.application.dto.LevelResponse;
import wooteco.prolog.studylog.application.dto.MissionResponse;
import wooteco.prolog.studylog.application.dto.TagResponse;

@RestController
@RequestMapping("/filters")
@AllArgsConstructor
public class FilterController {

    private final LevelService levelService;
    private final MissionService missionService;
    private final TagService tagService;
    private final MemberService memberService;

    @GetMapping
    public ResponseEntity<FilterResponse> showAll() {
        List<LevelResponse> levelResponses = levelService.findAll();
        List<MissionResponse> missionResponses = missionService.findAll();
        List<TagResponse> tagResponses = tagService.findTagsIncludedInPost();
        List<MemberResponse> memberResponses = memberService.findAll();
        return ResponseEntity.ok()
            .body(new FilterResponse(levelResponses, missionResponses, tagResponses, memberResponses));
    }
}
