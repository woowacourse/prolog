package wooteco.prolog.studylog.application;

import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import wooteco.prolog.login.ui.LoginMember;
import wooteco.prolog.session.application.MissionService;
import wooteco.prolog.session.application.SessionService;
import wooteco.prolog.session.application.dto.MissionResponse;
import wooteco.prolog.session.application.dto.SessionResponse;
import wooteco.prolog.studylog.application.dto.FilterResponse;

@Service
@AllArgsConstructor
public class FilterService {

    private final SessionService sessionService;
    private final MissionService missionService;

    public FilterResponse showAll(LoginMember loginMember) {
        List<SessionResponse> sessionResponses = sessionService.findAllOrderByDesc();
        List<MissionResponse> missionResponses = missionService.findAllWithMyMissionFirst(loginMember);

        List<SessionResponse> mySessions = sessionService.findMySessions(loginMember);

        return new FilterResponse(sessionResponses, mySessions, missionResponses);
    }

}
