package wooteco.prolog.roadmap.ui;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import wooteco.prolog.login.domain.AuthMemberPrincipal;
import wooteco.prolog.login.ui.LoginMember;
import wooteco.prolog.roadmap.application.RoadMapService;
import wooteco.prolog.roadmap.application.dto.KeywordsResponse;

@RequiredArgsConstructor
@RestController
public class RoadmapController {

    private final RoadMapService roadMapService;

    @GetMapping("/roadmaps")
    public KeywordsResponse findRoadMapKeyword(@AuthMemberPrincipal final LoginMember member,
                                               @RequestParam final Long curriculumId) {
        return roadMapService.findAllKeywordsWithProgress(curriculumId, member.getId());
    }
}
