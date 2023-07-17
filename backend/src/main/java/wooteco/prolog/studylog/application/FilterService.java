package wooteco.prolog.studylog.application;

import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import wooteco.prolog.login.ui.LoginMember;
import wooteco.prolog.member.application.MemberService;
import wooteco.prolog.member.application.dto.MemberResponse;
import wooteco.prolog.session.application.MissionService;
import wooteco.prolog.session.application.SessionService;
import wooteco.prolog.session.application.dto.MissionResponse;
import wooteco.prolog.session.application.dto.SessionResponse;
import wooteco.prolog.studylog.application.dto.FilterResponse;
import wooteco.prolog.studylog.application.dto.TagResponse;

@Service
@AllArgsConstructor
public class FilterService {

    private final SessionService sessionService;
    private final MissionService missionService;
    private final TagService tagService;
    private final MemberService memberService;

    public FilterResponse showAll(LoginMember loginMember) {
        List<SessionResponse> sessionResponses = sessionService.findAllWithMySessionFirst(
            loginMember); // my session으로 대체
        List<MissionResponse> missionResponses = missionService.findAllWithMyMissionFirst(
            loginMember);
        List<TagResponse> tagResponses = tagService.findTagsIncludedInStudylogs();
        List<MemberResponse> memberResponses = memberService.findAllOrderByNickNameAsc();
        return new FilterResponse(sessionResponses, missionResponses, tagResponses,
            memberResponses);
    }
}
