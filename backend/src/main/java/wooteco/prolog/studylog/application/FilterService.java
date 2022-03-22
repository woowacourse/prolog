package wooteco.prolog.studylog.application;

import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import wooteco.prolog.member.application.MemberService;
import wooteco.prolog.member.application.dto.MemberResponse;
import wooteco.prolog.session.application.LevelService;
import wooteco.prolog.session.application.MissionService;
import wooteco.prolog.session.application.dto.LevelResponse;
import wooteco.prolog.session.application.dto.MissionResponse;
import wooteco.prolog.studylog.application.dto.FilterResponse;
import wooteco.prolog.studylog.application.dto.TagResponse;

@Service
@AllArgsConstructor
public class FilterService {

    private final LevelService levelService;
    private final MissionService missionService;
    private final TagService tagService;
    private final MemberService memberService;

    public FilterResponse showAll() {
        List<LevelResponse> levelResponses = levelService.findAll();
        List<MissionResponse> missionResponses = missionService.findAll();
        List<TagResponse> tagResponses = tagService.findTagsIncludedInStudylogs();
        List<MemberResponse> memberResponses = memberService.findAll();
        return new FilterResponse(levelResponses, missionResponses, tagResponses, memberResponses);
    }
}
