package wooteco.prolog.studylog.application;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import wooteco.prolog.login.ui.LoginMember;
import wooteco.prolog.session.application.MissionService;
import wooteco.prolog.session.application.SessionService;
import wooteco.prolog.session.application.dto.MissionResponse;
import wooteco.prolog.session.application.dto.SessionResponse;
import wooteco.prolog.studylog.application.dto.FilterResponse;

import java.util.List;

@Service
@AllArgsConstructor
public class FilterService {

    private final SessionService sessionService;
    private final MissionService missionService;

    public FilterResponse showAll(LoginMember loginMember) {
        List<SessionResponse> sessionResponses = sessionService.findAllWithMySessionFirst(loginMember);
        List<MissionResponse> missionResponses = missionService.findAllWithMyMissionFirst(loginMember);
        return new FilterResponse(sessionResponses, missionResponses);
    }
}
